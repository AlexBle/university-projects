import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import com.example.hotel_book.User.*;

public class WorkerHandleRequest extends Thread {
    Socket requestSocket = null;
	ObjectInputStream in;
	ObjectOutputStream out;
    ArrayList<Hotel> hotels;
    ArrayList<Hotel> hotelsResult;
    ObjectOutputStream outToReducer;
	WorkerRequest workerRequest;

    String reducerIP;
    String reducerPort;

    public WorkerHandleRequest(Socket connection, ArrayList<Hotel> hotels, String reducerIP, String reducerPort){
        try {
			out = new ObjectOutputStream(connection.getOutputStream());
			in = new ObjectInputStream(connection.getInputStream());
            this.hotels = hotels;
            this.reducerIP = reducerIP;
            this.reducerPort = reducerPort;
		} catch (IOException e) {
			e.printStackTrace();
		}
    }   
    
    public void run() {
		try {
			try {
				MasterRequest masterRequest = (MasterRequest) in.readObject();
				System.out.println("In Worker : Received Request Type: " + masterRequest.getType());

                if(masterRequest.getType().equals("BOOK")){
                    book(masterRequest.getHotelName(), masterRequest.getDates());
                }
                else if(masterRequest.getType().equals("SEARCH")){
                    ArrayList<Hotel>  hotelsResult = searchHotels(masterRequest.getFilter());

                    requestSocket = new Socket(reducerIP, Integer.parseInt(reducerPort));
					workerRequest = new WorkerRequest("search", masterRequest.getRequestid(), hotelsResult, masterRequest.getLockIndex());
					outToReducer = new ObjectOutputStream(requestSocket.getOutputStream());
					outToReducer.writeObject(workerRequest);
					outToReducer.flush();
                }
                else if(masterRequest.getType().equals("ADD_REVIEW")){
                    addReview(masterRequest.getHotelName(), masterRequest.getReview());
                }
                else if(masterRequest.getType().equals("ADD_HOTEL")){
                    addHotel(masterRequest.getHotel());
                }
                else if(masterRequest.getType().equals("ADD_FREE_DATES")){
                    addFreeDates(masterRequest.getHotelName(), masterRequest.getDates());
                }
                else if(masterRequest.getType().equals("SHOW_HOTELS")){
                    ArrayList<Hotel>  hotelsResult =  showHotels(masterRequest.getManagerid());
                    //System.out.println(hotelsResult);
                    
                    requestSocket = new Socket(reducerIP, Integer.parseInt(reducerPort));
					workerRequest = new WorkerRequest("show_hotels", masterRequest.getRequestid(), hotelsResult, masterRequest.getLockIndex());
					outToReducer = new ObjectOutputStream(requestSocket.getOutputStream());
					outToReducer.writeObject(workerRequest);
					outToReducer.flush();
                }
                else if(masterRequest.getType().equals("AREA_BOOKINGS")){
                    HashMap<String, Integer> bookingsByArea = areaBookings(masterRequest.getDates());
                    System.out.println(bookingsByArea);
                    requestSocket = new Socket(reducerIP, Integer.parseInt(reducerPort));
                    workerRequest = new WorkerRequest("area_bookings", masterRequest.getRequestid(), bookingsByArea, masterRequest.getLockIndex());
					outToReducer = new ObjectOutputStream(requestSocket.getOutputStream());
					outToReducer.writeObject(workerRequest);
					outToReducer.flush();
                    
                }
				
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

    public int getHotelIndex(String hotelName){
        synchronized(this.hotels){
            for(int i = 0; i<hotels.size(); i++){
                if(hotels.get(i).getName().equals(hotelName)){
                    return i;
                }
            }
            System.out.println("Hotel \"" + hotelName + "\" not found!");
            return -1;
        }
    }
    
    public void book(String hotelName, int [] dates){
        int index = getHotelIndex(hotelName);
        int yearStart = dates[0];
        int monthStart = dates[1];
        int dateStart = dates[2];
        int yearEnd = dates[3];
        int monthEnd = dates[4];
        int dateEnd = dates[5];
        Date freeDatesStart = new Date(yearStart-1900, monthStart-1, dateStart);
        Date freeDatesEnd = new Date(yearEnd-1900, monthEnd-1, dateEnd);

        ArrayList<Date> bookingDates = new ArrayList<Date>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(freeDatesStart);
        while(!calendar.getTime().after(freeDatesEnd)){
            Date currenDate = calendar.getTime();
            bookingDates.add(currenDate);
            calendar.add(Calendar.DATE, 1);
        }
        boolean bookingAccepted = true;
        if(index != -1){
            synchronized(this.hotels.get(index)){
                for(Date bookingDate : bookingDates){
                    if(!this.hotels.get(index).getFreeDates().contains(bookingDate)){
                        bookingAccepted = false;
                        System.out.println("\n\nBooking Date needed: \n" + bookingDate);
                        System.out.println(this.hotels.get(index).getFreeDates());
                        break;
                    }
                }
                if(bookingAccepted){
                    System.out.println("Successfull Booking.");
                    this.hotels.get(index).getFreeDates().removeAll(bookingDates);
                    try {
                        out.writeObject("Successfull Booking.");
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // System.out.println("Remaining FreeDates:");
                    // System.out.println(this.hotels.get(index).getFreeDates());
                }
                else {
                    System.out.println("Unsuccessfull Booking.");
                    try {
                        out.writeObject("Unsuccessfull Booking.");
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // System.out.println(this.hotels.get(index).getFreeDates());
                }
            }
        }
        else{
            System.out.println("Unsuccessfull Booking.");
            try {
                out.writeObject("Unsuccessfull Booking.");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addHotel(Hotel hotelrec){
        synchronized(this.hotels){
            hotels.add(hotelrec);
            System.out.println("Hotel added.");
        }
    }

    public void addReview(String hotelName, int review){
        int index = getHotelIndex(hotelName);
        float newScore;
        synchronized(this.hotels.get(index)){
            this.hotels.get(index).addReview(review);
            System.out.println("Added Review.");
            newScore = this.hotels.get(index).getStars();
            System.out.println("New Star Score for Hotel \"" + this.hotels.get(index).getName() + "\" is: " + this.hotels.get(index).getStars());
        }
        try {
            out.writeObject(new String("Review added.\nNew Star Score for Hotel \"" + hotelName + "\" is: " + newScore));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFreeDates(String hotelName, int[] freeDates){
        int index = getHotelIndex(hotelName);
        synchronized(this.hotels.get(index)){
            this.hotels.get(index).addFreeDates(freeDates);
            //System.out.println(this.hotels.get(index).getFreeDates());
        }
    }
    
    public ArrayList<Hotel> showHotels(String managerid){
        ArrayList<Hotel> hotelsToReturn = new ArrayList<Hotel>();

        for(int i = 0; i<hotels.size(); i++){
            synchronized(this.hotels.get(i)){
                if(hotels.get(i).getManagerid().equals(managerid)){
                    hotelsToReturn.add(hotels.get(i));
                }
            }
        }
        return hotelsToReturn;
    }

    public ArrayList<Hotel> searchHotels(Filter filter){
        ArrayList<Hotel> hotelsToReturn = new ArrayList<Hotel>();
        boolean datesOK = filter.getAvailableDates() == null;
        boolean areaOK = filter.getArea() == null;
        boolean noOfPersonsOK = filter.getNoOfPersons() == 0;
        boolean starsOK = filter.getStars() == 0;
        // System.out.println(datesOK);
        // System.out.println(areaOK);
        // System.out.println(noOfPersonsOK);
        // System.out.println(starsOK);

        int[] dates = filter.getAvailableDates();
        int yearStart = dates[0];
        int monthStart = dates[1];
        int dateStart = dates[2];
        int yearEnd = dates[3];
        int monthEnd = dates[4];
        int dateEnd = dates[5];
        Date freeDatesStart = new Date(yearStart-1900, monthStart-1, dateStart);
        Date freeDatesEnd = new Date(yearEnd-1900, monthEnd-1, dateEnd);

        ArrayList<Date> bookingDates = new ArrayList<Date>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(freeDatesStart);
        while(!calendar.getTime().after(freeDatesEnd)){
            Date currenDate = calendar.getTime();
            bookingDates.add(currenDate);
            calendar.add(Calendar.DATE, 1);
        }

        for(int i = 0; i<hotels.size(); i++){
            datesOK = filter.getAvailableDates() == null;
            areaOK = filter.getArea() == null;
            noOfPersonsOK = filter.getNoOfPersons() == 0;
            starsOK = filter.getStars() == 0;
            synchronized(this.hotels.get(i)){
                if(!datesOK){
                    datesOK = true;
                    for(Date bookingDate : bookingDates){
                        if(!this.hotels.get(i).getFreeDates().contains(bookingDate)){
                            datesOK = false;
                            // System.out.println("\n\nBooking Date needed:\n" + bookingDate);
                            // System.out.println( this.hotels.get(i).getFreeDates());
                            break;
                        }
                    }
                }

                //System.out.println(this.hotels.get(i).getArea() + " == " + filter.getArea() + " is " + areaOK);
                if(!areaOK){
                    areaOK =  this.hotels.get(i).getArea().equals(filter.getArea());
                }
                if(!noOfPersonsOK){
                    noOfPersonsOK =  this.hotels.get(i).getNoOfPersons()>=filter.getNoOfPersons();
                }
                if(!starsOK){
                    starsOK =  this.hotels.get(i).getStars()>=filter.getStars();
                }

                if(datesOK && areaOK && noOfPersonsOK && starsOK){
                    // System.out.println(datesOK);
                    // System.out.println(areaOK);
                    // System.out.println(noOfPersonsOK);
                    // System.out.println(starsOK);
                    hotelsToReturn.add( this.hotels.get(i));
                }
            }
        }
        return hotelsToReturn;
    }

    public HashMap<String,Integer> areaBookings(int[] dates){
        HashMap<String, Integer> bookingByArea = new HashMap<String, Integer>();
        int yearStart = dates[0];
        int monthStart = dates[1];
        int dateStart = dates[2];
        int yearEnd = dates[3];
        int monthEnd = dates[4];
        int dateEnd = dates[5];
        Date freeDatesStart = new Date(yearStart-1900, monthStart-1, dateStart);
        Date freeDatesEnd = new Date(yearEnd-1900, monthEnd-1, dateEnd);

        ArrayList<Date> bookingDates = new ArrayList<Date>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(freeDatesStart);
        while(!calendar.getTime().after(freeDatesEnd)){
            Date currenDate = calendar.getTime();
            bookingDates.add(currenDate);
            calendar.add(Calendar.DATE, 1);
        }
        int counter;
        for(int i = 0; i<hotels.size(); i++){
            counter = 0;
            synchronized(this.hotels.get(i)){
                for(Date bookingDate : bookingDates){
                    if(!this.hotels.get(i).getFreeDates().contains(bookingDate)){
                        counter++;
                    }
                }
                if(bookingByArea.containsKey(hotels.get(i).getArea())){
                    bookingByArea.replace(hotels.get(i).getArea(), bookingByArea.get(hotels.get(i).getArea()) + counter);
                }
                else{
                    bookingByArea.put(hotels.get(i).getArea(), counter);
                }
            }
        }
        return bookingByArea;
    }
}
