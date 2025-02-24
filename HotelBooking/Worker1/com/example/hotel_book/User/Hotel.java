package com.example.hotel_book.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Hotel implements Serializable{
    String managerid;
    String name;
    int noOfPersons;
    String area;
    float stars;
    int noOfReviews;
    String roomImage;
    ArrayList<Date> freeDates = new ArrayList<Date>();

    public Hotel(String name, int noOfPersons, String area, float stars, int noOfReviews, String roomImage, String managerid){
        this.name = name;
        this.noOfPersons = noOfPersons;
        this.area = area;
        this.stars = stars;
        this.noOfReviews = noOfReviews;
        this.roomImage = roomImage;
        this.managerid = managerid;
    }

    public void addFreeDates(int[] newFreeDates){
        int yearStart = newFreeDates[0];
        int monthStart = newFreeDates[1];
        int dateStart = newFreeDates[2];
        int yearEnd = newFreeDates[3];
        int monthEnd = newFreeDates[4];
        int dateEnd = newFreeDates[5];
        
        Date freeDatesStart = new Date(yearStart-1900, monthStart-1, dateStart);
        Date freeDatesEnd = new Date(yearEnd-1900, monthEnd-1, dateEnd);

        ArrayList<Date> freeDatesToAdd = new ArrayList<Date>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(freeDatesStart);
        while(!calendar.getTime().after(freeDatesEnd)){
            Date currenDate = calendar.getTime();
            freeDatesToAdd.add(currenDate);
            calendar.add(Calendar.DATE, 1);
        }

        for(Date date : freeDatesToAdd){
            if(!freeDates.contains(date)){
                freeDates.add(date);
            }
        }
    }

    public void addReview(int review){
        float total = getStars()*getNoOfReviews();
        total += review;
        this.noOfReviews++;
        this.stars = total/noOfReviews;
    }

    public String getName() {
        return name;
    }

    public int getNoOfPersons() {
        return noOfPersons;
    }

    public String getArea() {
        return area;
    }

    public float getStars() {
        return stars;
    }

    public int getNoOfReviews() {
        return noOfReviews;
    }

    public String getRoomImage() {
        return roomImage;
    }

    public ArrayList<Date> getFreeDates() {
        return freeDates;
    }

    public String getManagerid() {
        return managerid;
    }

    public String toString(){
        return "Hotel name: " + name + 
        "\nNumber of Persons: " + Integer.toString(noOfPersons) + 
        "\nArea: " + area + 
        "\nStars: " + Float.toString(stars) + 
        "\nNumber of Reviews: " + Integer.toString(noOfReviews) + 
        "\nRoom Image: " + roomImage;
    }
}
