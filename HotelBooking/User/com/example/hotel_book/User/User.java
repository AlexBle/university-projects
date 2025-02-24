package com.example.hotel_book.User;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

public class User implements Serializable{
    String name;
    String userid;
    UserRequest userRequest;
    int requests = 0;
    String masterIP;
    String masterPort;

    public User(String name, String userid){
        String fileName = "configUser.txt"; 
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
        this.userid = userid;
    }

    public ArrayList<Hotel> search(Filter filter){
        requests++;
        userRequest = new UserRequest("UserSearch", this.getUserid() + " " + Integer.toString(requests), filter);
        ArrayList<Hotel> hotels = this.run();
        return hotels;
    }

    public void book(String hotelName, int bookingDates[]){
        requests++;
        userRequest = new UserRequest("UserBook", this.getUserid() + " " + Integer.toString(requests) ,hotelName, bookingDates);
        this.run();
    }

    public void review(String hotelName, int reviewScore){
        requests++;
        userRequest = new UserRequest("UserReview", this.getUserid() + " " + Integer.toString(requests), hotelName, reviewScore);
        this.run();
    }

    public String toString(){
        return name;
    }

    public UserRequest getUserRequest() {
        return userRequest;
    }

    public String getUserid() {
        return userid;
    }

    public ArrayList<Hotel> run() {
		Socket requestSocket = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		try {
			requestSocket = new Socket(masterIP, Integer.parseInt(masterPort));
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			in = new ObjectInputStream(requestSocket.getInputStream());

			
			out.writeObject(getUserRequest());
			out.flush();

            Object object = in.readObject();
			System.out.println("Server>" + object);
            if(((String) object).equals("Received Request type: UserSearch")){
                object = in.readObject();
                if(object.getClass().equals(ThreadPass.class)){
                    ArrayList<Hotel> hotelsResult = (ArrayList<Hotel>) ((ThreadPass) object).getObject();
                    System.out.println("\nResult of Search:");
                    System.out.println(hotelsResult);
                    return hotelsResult;
                }
            }
            else{
                object = in.readObject();
                System.out.println("Server>" + object);
                return null;
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
				requestSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
        return null;
	}
    public static void main(String args[]) {
        User u1 = new User("Yusef","1");
        
        int [] bookingDates = new int[6];
        bookingDates[0] = 2024;
        bookingDates[1] = 1;
        bookingDates[2] = 1;
        bookingDates[3] = 2024;
        bookingDates[4] = 1;
        bookingDates[5] = 2;
        // u1.book("Hotel1", bookingDates);
        // User u2 = new User("Moukios","2");
        // u1.book("BAD NAME", bookingDates);
        // u2.book("Athens Modern Stay", bookingDates);

        // u1.review("Athens Modern Stay", 5);

        Random r = new Random();
        int checkin;
        int checkout;
        int hotelid;
        User u;
        int userid = 3;
        String[] greekNames = {
            "Alexandros",
            "Sophia",
            "Dimitris",
            "Eleni",
            "Georgios",
            "Anastasia",
            "Nikolaos",
            "Ekaterini",
            "Panagiotis",
            "Maria"
        };

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

        for(int i = 0; i<30; i++){
            checkin = r.nextInt(28-1) + 1;
            checkout = checkin + 3;
            bookingDates[2] = checkin;
            bookingDates[5] = checkout;
            hotelid = r.nextInt(6-1)+1;
            u = new User(greekNames[r.nextInt(10)], Integer.toString(userid));
            userid++;
            u.book(roomNames[r.nextInt(20)], bookingDates);
        }

        // bookingDates[0] = 2024;
        // bookingDates[1] = 1;
        // bookingDates[2] = 1;
        // bookingDates[3] = 2024;
        // bookingDates[4] = 1;
        // bookingDates[5] = 2;

        // u1.book("Athens Modern Stay", bookingDates);
        // u1.book("Messenian Manor",bookingDates);
        // u1.book("Patra Comfort Inn", bookingDates);
        // u1.book("Athens Urban Villa",bookingDates);

        u1.search(new Filter(bookingDates, "Athens", 2, 4));
    }

}
