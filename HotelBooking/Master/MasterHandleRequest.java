import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import com.example.hotel_book.User.*;


public class MasterHandleRequest extends Thread{
	Socket requestSocket = null;
	ObjectInputStream in;
	ObjectOutputStream out;
	int numberofNodes;
	String [][] workerIPs;
	UserRequest userRequest;
	Hotel hotel;
	int workerid;
	MasterRequest masterRequest;
	ObjectOutputStream outToWorker;
	ObjectInputStream inFromWorker;
	ManagerRequest managerRequest;
	ReducerRequest reducerRequest;
	String hotelName;
	ArrayList<Hotel> hotelsResult;
	int[] bookingDates = null;
    int reviewScore;
	Integer lockIndex;
	ArrayList<ThreadPass> locks;
	Integer notifyLockIndex;

	public MasterHandleRequest(Socket connection, int numberofNodes, String [][] workerIPs, ArrayList<ThreadPass> locks, int lockIndex) {
		try {
			out = new ObjectOutputStream(connection.getOutputStream());
			in = new ObjectInputStream(connection.getInputStream());
			this.numberofNodes = numberofNodes;
			this.workerIPs = workerIPs;
			this.lockIndex = lockIndex;
			this.locks = locks;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int hash(String name){
		int hash = 7;
		for (int i = 0; i < name.length(); i++) {
			hash = hash*31 + name.charAt(i);
		}
		int workerindex = Math.abs(hash % numberofNodes);
		return workerindex;
	}

	public void run() {
		try {
			Request request;
			try {
				request = (Request) in.readObject();
				System.out.println("In Master : Received Request Type: " + request.getType());
				out.writeObject("Received Request type: " + request.getType());
				out.flush();
				
                if(request.getType().equals("UserBook")){
                    System.out.println("Book");
					userRequest = (UserRequest) request;
					hotelName = userRequest.getHotelName();
					workerid = hash(hotelName);
					bookingDates = userRequest.getBookingDates();
					
					requestSocket = new Socket(workerIPs[workerid][0], Integer.parseInt(workerIPs[workerid][1]));
					masterRequest = new MasterRequest("BOOK",userRequest.getRequestid(), hotelName, bookingDates);
					outToWorker = new ObjectOutputStream(requestSocket.getOutputStream());
					outToWorker.writeObject(masterRequest);
					outToWorker.flush();
					
					inFromWorker = new ObjectInputStream(requestSocket.getInputStream());		
					
					Object o = inFromWorker.readObject();
					System.out.println(o);
					out.writeObject(o);			
					out.flush();

                }
				else if(request.getType().equals("UserSearch")){
					System.out.println("Search");
					userRequest = (UserRequest) request;

					for(workerid = 0; workerid<numberofNodes; workerid++){
						requestSocket = new Socket(workerIPs[workerid][0], Integer.parseInt(workerIPs[workerid][1]));
						masterRequest = new MasterRequest("SEARCH", userRequest.getRequestid(), userRequest.getFilter(), lockIndex);
						outToWorker = new ObjectOutputStream(requestSocket.getOutputStream());
						outToWorker.writeObject(masterRequest);
						outToWorker.flush();
					}
					synchronized(locks.get(lockIndex)){
						locks.get(lockIndex).wait();
						System.out.println(locks.get(lockIndex).getObject().toString());
						out.writeObject(locks.get(lockIndex));
						out.flush();
					}
				}
				else if(request.getType().equals("search_result")){
					System.out.println("SearchResult");
					reducerRequest = (ReducerRequest) request;

					hotelsResult = reducerRequest.getHotelsResult();
					notifyLockIndex = reducerRequest.getLockIndex();
					synchronized(locks.get(notifyLockIndex)){
						locks.get(notifyLockIndex).setObject(hotelsResult);
						locks.get(notifyLockIndex).notifyAll();
					}
				}
                else if(request.getType().equals("UserReview")){
                    System.out.println("Review");
					userRequest = (UserRequest) request;
					workerid = hash(userRequest.getHotelName());

					requestSocket = new Socket(workerIPs[workerid][0], Integer.parseInt(workerIPs[workerid][1]));
					masterRequest = new MasterRequest("ADD_REVIEW", userRequest.getRequestid(), userRequest.getReviewScore(), userRequest.getHotelName());
					outToWorker = new ObjectOutputStream(requestSocket.getOutputStream());
					outToWorker.writeObject(masterRequest);
					outToWorker.flush();

					
					inFromWorker = new ObjectInputStream(requestSocket.getInputStream());
					out.writeObject(inFromWorker.readObject());
					out.flush();

					
                }
				else if(request.getType().equals("AddHotel")){
					System.out.println("AddHotel");
					managerRequest = (ManagerRequest) request;
					hotel = managerRequest.getHotel();
					workerid = hash(hotel.getName());

					requestSocket = new Socket(workerIPs[workerid][0], Integer.parseInt(workerIPs[workerid][1]));
					masterRequest = new MasterRequest("ADD_HOTEL",managerRequest.getRequestid(), hotel);
					outToWorker = new ObjectOutputStream(requestSocket.getOutputStream());
					outToWorker.writeObject(masterRequest);
					outToWorker.flush();

				}
				else if(request.getType().equals("AddFreeDates")){
					System.out.println("AddFreeDates");
					managerRequest = (ManagerRequest) request;
					hotelName = managerRequest.getHotelName();
					workerid = hash(hotelName);

					requestSocket = new Socket(workerIPs[workerid][0], Integer.parseInt(workerIPs[workerid][1]));
					masterRequest = new MasterRequest("ADD_FREE_DATES",managerRequest.getRequestid(), hotelName, managerRequest.getFreeDates());
					outToWorker = new ObjectOutputStream(requestSocket.getOutputStream());
					outToWorker.writeObject(masterRequest);
					outToWorker.flush();
					
				}
				else if(request.getType().equals("ShowHotels")){
					System.out.println("ShowHotels");
					managerRequest = (ManagerRequest) request;

					for(workerid = 0; workerid<numberofNodes; workerid++){
						requestSocket = new Socket(workerIPs[workerid][0], Integer.parseInt(workerIPs[workerid][1]));
						masterRequest = new MasterRequest("SHOW_HOTELS",managerRequest.getRequestid(),managerRequest.getManagerid(), lockIndex);
						outToWorker = new ObjectOutputStream(requestSocket.getOutputStream());
						outToWorker.writeObject(masterRequest);
						outToWorker.flush();
					}
					synchronized(locks.get(lockIndex)){
						locks.get(lockIndex).wait();
						out.writeObject(locks.get(lockIndex));
						out.flush();
					}
				}
				else if(request.getType().equals("show_hotels_result")){
					System.out.println("ShowHotelsResult");
					reducerRequest = (ReducerRequest) request;

					hotelsResult = reducerRequest.getHotelsResult();
					notifyLockIndex = reducerRequest.getLockIndex();
					synchronized(locks.get(notifyLockIndex)){
						locks.get(notifyLockIndex).setObject(hotelsResult);
						locks.get(notifyLockIndex).notifyAll();
					}
				}
				else if(request.getType().equals("AreaBookings")){
					System.out.println("AreaBookings");
					managerRequest = (ManagerRequest) request;

					for(workerid = 0; workerid<numberofNodes; workerid++){
						requestSocket = new Socket(workerIPs[workerid][0], Integer.parseInt(workerIPs[workerid][1]));
						masterRequest = new MasterRequest("AREA_BOOKINGS", managerRequest.getRequestid(), managerRequest.getFreeDates(), lockIndex);
						outToWorker = new ObjectOutputStream(requestSocket.getOutputStream());
						outToWorker.writeObject(masterRequest);
						outToWorker.flush();
					}
					synchronized(locks.get(lockIndex)){
						locks.get(lockIndex).wait();
						out.writeObject(locks.get(lockIndex));
						out.flush();
					}
				}
				else if(request.getType().equals("area_bookings_result")){
					System.out.println("AreaBookingsResult");
					reducerRequest = (ReducerRequest) request;

					HashMap<String,Integer> areaBookingsResults = reducerRequest.getAreaBookingsResults();
					notifyLockIndex = reducerRequest.getLockIndex();
					synchronized(locks.get(notifyLockIndex)){
						locks.get(notifyLockIndex).setObject(areaBookingsResults);
						locks.get(notifyLockIndex).notifyAll();
					}
				}

				
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			}catch (EOFException eofException) {
				// Handle EOFException
				System.err.println("EOFException: End of input stream reached unexpectedly.");
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}
}


