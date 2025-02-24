package com.example.hotel_book.User;

import java.io.Serializable;

public class Filter implements Serializable{
    
    int [] availableDates = new int[6];
    String area;
    int noOfPersons;
    float stars;

    public Filter(int[] availableDates, String area, int noOfPersons, float stars){
        this.availableDates = availableDates;
        this.area = area;
        this.noOfPersons = noOfPersons;
        this.stars = stars;
    }

    public int[] getAvailableDates() {
        return availableDates;
    }

    public String getArea() {
        return area;
    }

    public int getNoOfPersons() {
        return noOfPersons;
    }

    public float getStars() {
        return stars;
    }

}
