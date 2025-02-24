package com.example.hotel_book.User;

public class UserRequest extends Request{
    String hotelName = null;
    int[] bookingDates = null;
    int reviewScore;
    Filter filter;

    public UserRequest(String type, String requestid, String hotelName, int [] bookingDates){
        super(type, requestid);
        this.hotelName = hotelName;
        this.bookingDates = bookingDates;
    }

    public UserRequest(String type, String requestid, String hotelName, int reviewScore){
        super(type, requestid);
        this.hotelName = hotelName;
        this.reviewScore = reviewScore;
    }

    public UserRequest(String type, String requestid){
        super(type, requestid);
    }

    public UserRequest(String type, String requestid, Filter filter){
        super(type, requestid);
        this.filter = filter;
    }
    
    public String getHotelName() {
        return hotelName;
    }

    public int[] getBookingDates() {
        return bookingDates;
    }

    public int getReviewScore() {
        return reviewScore;
    }

    public Filter getFilter() {
        return filter;
    }

}

