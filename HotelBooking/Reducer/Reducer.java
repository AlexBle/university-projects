import java.io.*;
import java.net.*;
import java.util.ArrayList;
import com.example.hotel_book.User.*;
public class Reducer {
    ServerSocket providerSocket;
    Socket requestSocket = null;
	Socket connection = null;
    ObjectInputStream in;
    ObjectOutputStream out;
    ArrayList<Hotel> hotelsResult;
	ReducerRequest reducerRequest;
	String ownIP;
	String ownPort;
	String masterIP;
	String masterPort;
	ArrayList<WorkerRequest[]> familyOfRequests = new ArrayList<WorkerRequest[]>();
    
    public Reducer(){
        String fileName = "configReducer.txt"; 
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim().replaceAll("\"", "");
                    switch (key) {
                        case "Own IP": this.ownIP = value; break;
                        case "Own port": this.ownPort = value; break;
                        case "Master IP": this.masterIP = value; break;
                        case "Master port": this.masterPort = value; break;
                        default: break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Reducer().openServer();
    }

	void openServer() {
		try {
			providerSocket = new ServerSocket(Integer.parseInt(ownPort), 10);
			
			while (true) {
				connection = providerSocket.accept();
				ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
				WorkerRequest workerRequest = (WorkerRequest) in.readObject();
				System.out.println("In Reducer : Received Request Type: " + workerRequest.getType() + " REQUEST ID: " + workerRequest.getRequestid());
				
				String requestid = workerRequest.getRequestid();
				boolean foundFamily = false;
				for(WorkerRequest[] family : familyOfRequests){
					if(family[0].getRequestid().equals(requestid)){
						foundFamily = true;
						if(family[1] == null){
							family[1] = workerRequest;
						}
						else{
							family[2] = workerRequest;
							Thread t = new ReducerHandleRequest(family, connection, masterIP, masterPort);
							familyOfRequests.remove(family);
							t.start();
							break;
						}
					}
				}
				if(!foundFamily){
					WorkerRequest[] requestFamily = new WorkerRequest[3];
					requestFamily[0] = workerRequest;
					familyOfRequests.add(requestFamily);
				}
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				providerSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
    }
}
