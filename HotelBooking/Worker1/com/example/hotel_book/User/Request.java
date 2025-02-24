package com.example.hotel_book.User;

import java.io.Serializable;

public class Request implements Serializable{
    String type;
    String requestid;
    public Request(String type, String requestid){
        this.type = type;
        this.requestid = requestid;
    }

    public String getType() {
        return type;
    }

    public String getRequestid() {
        return requestid;
    }
    
}