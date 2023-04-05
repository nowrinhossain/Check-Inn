package com.example.nowrin.myapplication;

public class HotelRoomClass {

    private String hotelname;
    private String roomStatus;
    private int bednumbers;
    private int adult_guestnumber;
    private int child_guestnumber;
    private double cost;
    private String thisRoomID;
    private String thisRoomImage;
    private String extraAdvantage;
    private String hotelID;



    public double getCost() {
        return cost;
    }


    public int getBednumbers() {
        return bednumbers;
    }


    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }

    public String getHotelname() { return hotelname; }

    public String getThisRoomImage() {
        return thisRoomImage;
    }

    public String getThisRoomID() {
        return thisRoomID;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public int getAdult_guestnumber() {
        return adult_guestnumber;
    }

    public int getChild_guestnumber() {
        return child_guestnumber;
    }


    public String getExtraAdvantage() {
        return extraAdvantage;
    }

    public void setExtraAdvantage(String extraAdvantage) {
        this.extraAdvantage = extraAdvantage;
    }

    public void setThisRoomImage(String thisRoomImage) {
        this.thisRoomImage = thisRoomImage;
    }

    public void setThisRoomID(String thisRoomID) {
        this.thisRoomID = thisRoomID;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setName(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public void setBednumbers(int bednumbers) {
        this.bednumbers = bednumbers;
    }


    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public void setAdult_guestnumber(int adult_guestnumber) { this.adult_guestnumber = adult_guestnumber; }

    public void setChild_guestnumber(int child_guestnumber) { this.child_guestnumber = child_guestnumber; }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }





}
