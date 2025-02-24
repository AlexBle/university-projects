import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import com.example.hotel_book.User.*;

public class Master {

    ServerSocket providerSocket;
	Socket connection = null;
    ObjectInputStream in;
    ObjectOutputStream out;
	int numberofNodes = 3;
	String[][] workersIPs = new String[numberofNodes][2];
	ArrayList<ThreadPass> locks = new ArrayList<ThreadPass>();
	int lockIndex = 0;
	String ownIP;
	String ownPort;
	String reducerIP;
	String reducerPort;

    public Master(){
		String fileName = "configMaster.txt"; 
        
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
                        case "Worker1 IP": this.workersIPs[0][0] = value; break;
                        case "Worker1 port": this.workersIPs[0][1] = value; break;
                        case "Worker2 IP": this.workersIPs[1][0] = value; break;
                        case "Worker2 port": this.workersIPs[1][1] = value; break;
                        case "Worker3 IP": this.workersIPs[2][0] = value; break;
                        case "Worker3 port": this.workersIPs[2][1] = value; break;
                        case "Reducer IP": this.reducerIP = value; break;
                        case "Reducer port": this.reducerPort = value; break;
                        default: break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
		new Master().openServer();
	}
	
	void openServer() {
		try {
			providerSocket = new ServerSocket(Integer.parseInt(ownPort), 10);
			
			while (true) {
				connection = providerSocket.accept();
				ThreadPass tp = new ThreadPass(lockIndex);
				locks.add(tp);
				Thread t = new MasterHandleRequest(connection, numberofNodes, workersIPs, locks, locks.indexOf(tp));
				lockIndex++;
				t.start();
				
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			try {
				providerSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}
}
