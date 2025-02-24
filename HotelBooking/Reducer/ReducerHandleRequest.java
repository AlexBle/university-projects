import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import com.example.hotel_book.User.*;
public class ReducerHandleRequest extends Thread{
    Socket requestSocket = null;
	ObjectInputStream in;
	ObjectOutputStream out;
    ArrayList<Hotel> hotelsResult;
    ObjectOutputStream outToClient;
	ReducerRequest reducerRequest;
    ObjectOutputStream outToMaster;
    WorkerRequest[] requestFamily;

    String masterIP;
    String masterPort;

    public ReducerHandleRequest(WorkerRequest[] requestFamily, Socket connection, String masterIP, String masterPort){
        this.requestFamily = requestFamily;
        this.masterIP = masterIP;
        this.masterPort = masterPort;
        try {
            out = new ObjectOutputStream(connection.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 

    public void run(){
        try {
            WorkerRequest workerRequest = requestFamily[0];
            System.out.println("In Reducer Thread: Received Request Type: " + workerRequest.getType());
            hotelsResult = new ArrayList<Hotel>();
            if(workerRequest.getType().equals("show_hotels") || workerRequest.getType().equals("search")){
                for(int i = 0; i<3; i++){
                    if(!(requestFamily[i].getHotelsResult().size() == 0 )){
                        for(Hotel hotel : requestFamily[i].getHotelsResult()){
                            hotelsResult.add(hotel);
                        }
                    }
                }

                requestSocket = new Socket(masterIP, Integer.parseInt(masterPort));
                reducerRequest = new ReducerRequest(workerRequest.getType() + "_result", workerRequest.getRequestid(), hotelsResult, workerRequest.getLockIndex());
                outToMaster = new ObjectOutputStream(requestSocket.getOutputStream());
                outToMaster.writeObject(reducerRequest);
                outToMaster.flush();
            }
            else if(workerRequest.getType().equals("area_bookings")){
                HashMap<String, Integer> areaBookingsResults = new HashMap<String,Integer>();
                for(int i = 0; i<3; i++){
                    for(String area : requestFamily[i].getBookingByArea().keySet()){
                        if(areaBookingsResults.containsKey(area)){
                            areaBookingsResults.replace(area, areaBookingsResults.get(area) + requestFamily[i].getBookingByArea().get(area)); 
                        }
                        else{
                            areaBookingsResults.put(area, requestFamily[i].getBookingByArea().get(area));
                        }
                    }
                }

                requestSocket = new Socket(masterIP, Integer.parseInt(masterPort));
                reducerRequest = new ReducerRequest("area_bookings_result", workerRequest.getRequestid(), areaBookingsResults, workerRequest.getLockIndex());
                outToMaster = new ObjectOutputStream(requestSocket.getOutputStream());
                outToMaster.writeObject(reducerRequest);
                outToMaster.flush();
            }
            
            out.writeUTF("Received obj: " + workerRequest.getType());
            out.flush();
        } catch (IOException ioException) {
			ioException.printStackTrace();
		} 
    }
}


