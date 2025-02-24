import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import com.example.hotel_book.User.*;
public class Worker {

    ServerSocket providerSocket;
	Socket connection = null;
    ObjectInputStream in;
    ObjectOutputStream out;
    ArrayList<Hotel> hotels = new ArrayList<Hotel>();
	int portNumber;
	String ownIP;
	String ownPort;
	String reducerIP;
	String reducerPort;

    public Worker(){
        
    }

    public static void main(String[] args) {
		new Worker().openServer();
	}

    void openServer() {
		try {
			String fileName = "configWorker.txt";
			try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
				String line;
				while ((line = br.readLine()) != null) {
					String[] parts = line.split(":");
					if (parts.length == 2) {
						String key = parts[0].trim(); 
						String value = parts[1].trim().replaceAll("\"", "");
						switch (key) {
							case "Own IP": ownIP = value; break;
							case "Own port": ownPort = value; break;
							case "Reducer IP": reducerIP = value; break;
							case "Reducer port": reducerPort = value; break;
							default: break;
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			providerSocket = new ServerSocket(Integer.parseInt(ownPort), 10);
			
			while (true) {
				connection = providerSocket.accept();

				Thread t = new WorkerHandleRequest(connection, hotels, reducerIP, reducerPort);
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
