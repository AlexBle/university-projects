import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import com.example.hotel_book.User.*;

public class Manager implements Serializable{
    private static final long serialVersionUID = 1L;
    String name;
    String managerid;
    ManagerRequest managerRequest;
    int requests = 0;
    String masterIP;
    String masterPort;
    transient ServerSocket providerSocket;
	transient Socket connection = null;

    public Manager(String name, String managerid){
        String fileName = "configManager.txt"; 
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim().replaceAll("\"", "");
                    switch (key) {
                        case "Master IP": this.masterIP = value; break;
                        case "Master port": this.masterPort = value; break;
                        default: break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.name = name;
        this.managerid = managerid;
    }
    

    public void addHotel(String name, int noOfPersons, String area, float stars, int noOfReviews, String roomImage){
        Hotel newHotel = new Hotel(name, noOfPersons, area, stars, noOfReviews, roomImage, this.getManagerid());
        requests++;
        managerRequest = new ManagerRequest("AddHotel", this.getManagerid() + " " + Integer.toString(requests), newHotel);
        this.run();
    }

    public void addFreeDates(String hotelName, int yearStart, int monthStart, int dateStart, int yearEnd, int monthEnd, int dateEnd){
        int [] freeDates = new int[6];
        freeDates[0] = yearStart;
        freeDates[1] = monthStart;
        freeDates[2] = dateStart;
        freeDates[3] = yearEnd;
        freeDates[4] = monthEnd;
        freeDates[5] = dateEnd;
        requests++;
        managerRequest = new ManagerRequest("AddFreeDates", this.getManagerid() + " " + Integer.toString(requests), hotelName, freeDates);
        this.run();   
    }

    public void showHotels(){
        requests++;
        managerRequest = new ManagerRequest(this.getManagerid(), "ShowHotels", this.getManagerid() + " " + Integer.toString(requests));
        this.run();
    }

    public void areaBookings(int yearStart, int monthStart, int dateStart, int yearEnd, int monthEnd, int dateEnd){
        int [] dates = new int[6];
        dates[0] = yearStart;
        dates[1] = monthStart;
        dates[2] = dateStart;
        dates[3] = yearEnd;
        dates[4] = monthEnd;
        dates[5] = dateEnd;
        requests++;
        managerRequest = new ManagerRequest("AreaBookings", this.getManagerid() + " " + Integer.toString(requests), dates);
        this.run();
    }

    public String getManagerName(){
        return name;
    }
    
    public String getManagerid() {
        return managerid;
    }

    public ManagerRequest getManagerRequest() {
        return managerRequest;
    }

    public String getName() {
        return name;
    }


    public void run() {
		Socket requestSocket = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		try {
			requestSocket = new Socket(masterIP, Integer.parseInt(masterPort));
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			in = new ObjectInputStream(requestSocket.getInputStream());

			
			out.writeObject(getManagerRequest());
			out.flush();

			Object object = in.readObject();

            if(object.getClass().equals(String.class)){
                System.out.println("Server> " + (String) object);
                if(((String) object).equals("Received Request type: ShowHotels")){
                    object = in.readObject();
                    if(object.getClass().equals(ThreadPass.class)){
                        ArrayList<Hotel> hotelsResult = (ArrayList<Hotel>) ((ThreadPass) object).getObject();
                        System.out.println("\nHotels of Manager " + this.getManagerName() + ":");
                        System.out.println(hotelsResult);
                    }
                }
                else if(((String) object).equals("Received Request type: AreaBookings")){
                    object = in.readObject();
                    if(object.getClass().equals(ThreadPass.class)){
                        HashMap<String,Integer> areaBookings = (HashMap<String,Integer>) ((ThreadPass) object).getObject();
                        System.out.println("\nBookings by Area:");
                        System.out.println(areaBookings);
                    }
                }
            }
	
		} catch (UnknownHostException unknownHost) {
			System.err.println("You are trying to connect to an unknown host!");
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
			try {
				in.close();	out.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

    public void addHotelsFromJSON(String filePath) {
        StringBuilder jsonBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = jsonBuilder.toString();
        json = json.substring(1, json.length() - 1);

        String[] hotelStrings = json.split("\\},\\s*\\{");
        for (String hotelString : hotelStrings) {
            hotelString = hotelString.replace("\"", "");
            String[] keyValuePairs = hotelString.split(",");
            String hotelManagerId = null;
            String name = null;
            int noOfPersons = 0;
            String area = null;
            float stars = 0;
            int noOfReviews = 0;
            String roomImage = null;

            for (String pair : keyValuePairs) {
                String[] entry = pair.split(":");
                String key = entry[0].trim();
                String value = entry[1].trim().replaceAll("\"", "");
                //System.out.println(key + ": " + value);

                if (key.equals("roomName")) {
                    name = value;
                } else if (key.equals("noOfPersons")) {
                    noOfPersons = Integer.parseInt(value);
                } else if (key.equals("area")) {
                    area = value;
                } else if (key.equals("stars")) {
                    stars = Float.parseFloat(value);
                } else if (key.equals("noOfReviews")) {
                    noOfReviews = Integer.parseInt(value);
                } else if (key.equals("roomImage")) {
                    roomImage = value;
                } else if (key.equals("managerid")) {
                    hotelManagerId = value;
                }
            }

            // Create a Hotel object only if its managerid matches the manager's id
            if (hotelManagerId != null && name != null) {
                // Hotel hotel = new Hotel(name, noOfPersons, area, stars, noOfReviews, roomImage, hotelManagerId);
                // System.out.println("\n\nHOTEL:");
                // System.out.println(hotel);
                this.addHotel(name, noOfPersons, area, stars, noOfReviews, roomImage);
            }
        }
    }


    public static void main(String[] args) {
        Manager manager1 = new Manager("Kostas", "manager1");

        String filePath = "hotels.json";
        manager1.addHotelsFromJSON(filePath);
        

        // manager1.addHotel("Hotel1", 5, "TEST AREA", 4, 1, "1 Image");
        // manager2.addHotel("Hotel2", 3, "Athens", 0, 0, "2 Image");
    
        // manager1.addHotel("Hotel3", 4, "Athens", 4, 5, "3 Image");
        // manager2.addHotel("Hotel4", 2, "Athens", 4.5f, 11, "4 Image");

        // manager1.addHotel("Hotel5", 3, "Volos", 4.65f, 33, "5 Image");
        // manager2.addHotel("Hotel6", 5, "Volos", 4.1f, 41, "6 Image");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        manager1.showHotels();

        String[] roomNames = {
            "Athens Modern Stay",
            "Patra Marina Hotel",
            "Thessaloniki Royal Suites",
            "Kalamata Bay Inn",
            "Volos View Hotel",
            "Capital Retreat Athens",
            "Peloponnese Gateway",
            "Thessaloniki Grand Palace",
            "Olive Grove Resort",
            "Volos Nautical Rooms",
            "Athens Urban Villa",
            "Patra Portside Hotel",
            "Thessaloniki City Hotel",
            "Kalamata Sunset Resort",
            "Volos Mountain View Hotel",
            "Acropolis View Luxury",
            "Patra Comfort Inn",
            "Northern Lights Hotel",
            "Messenian Manor",
            "Pelion Seaside Resort"
        };

        for(int i = 0; i<20; i++){
            manager1.addFreeDates(roomNames[i], 2024, 1, 1, 2024, 1, 31);
        }

        // manager1.addFreeDates("Hotel1", 2024, 1, 1, 2024, 1, 31);
        // manager2.addFreeDates("Hotel2", 2024, 1, 1, 2024, 1, 31);

        // manager1.addFreeDates("Hotel3", 2024, 1, 1, 2024, 1, 31);
        // manager2.addFreeDates("Hotel4", 2024, 1, 1, 2024, 1, 31);

        // manager1.addFreeDates("Hotel5", 2024, 1, 1, 2024, 1, 31);
        // manager2.addFreeDates("Hotel6", 2024, 1, 1, 2024, 1, 31);

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            
        manager1.areaBookings(2024,1,1,2024,1,31);
    }
    
}