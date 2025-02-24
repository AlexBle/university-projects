package com.example.hotel_book.User;

import java.io.Serializable;

public class ThreadPass implements Serializable{
    Object object;
    public ThreadPass(Object object){
        this.object = object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }
}
