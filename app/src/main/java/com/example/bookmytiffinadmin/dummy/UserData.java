package com.example.bookmytiffinadmin.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserData{
    private String dateOfBooking;
    private String nameOfCustomer;
    private String phoneNumber;


    public UserData(String dateOfBooking, String nameOfCustomer, String phoneNumber) {
        this.dateOfBooking = dateOfBooking;
        this.nameOfCustomer = nameOfCustomer;
        this.phoneNumber = phoneNumber;
    }

    public UserData() {
    }

    public String getDateOfBooking() {
        return dateOfBooking;
    }

    public void setDateOfBooking(String dateOfBooking) {
        this.dateOfBooking = dateOfBooking;
    }

    public String getNameOfCustomer() {
        return nameOfCustomer;
    }

    public void setNameOfCustomer(String nameOfCustomer) {
        this.nameOfCustomer = nameOfCustomer;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}