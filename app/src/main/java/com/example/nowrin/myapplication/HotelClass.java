package com.example.nowrin.myapplication;

import java.util.ArrayList;
import java.util.List;

public class HotelClass {

    private String hotelID;
    private String name;
    private ArrayList<String> contactInfo;
    private String mailaddress;
    //private List<HotelRoomClass> hotelRoom;
    private String websiteLink;
    private String country;
    private String city;
    private String address;
    private String image;

    public HotelClass(){

        contactInfo = new ArrayList<>();
    }




    private HotelClass(hotelBuilder hBuilder){
        contactInfo = new ArrayList<String>();
        this.hotelID = hBuilder.hotelID;
        this.name = hBuilder.name;
        this.contactInfo = hBuilder.contactInfo;
        this.mailaddress = hBuilder.mailaddress;
        this.websiteLink = hBuilder.websiteLink;
        this.country = hBuilder.country;
        this.city = hBuilder.city;
        this.address = hBuilder.address;
        this.image = hBuilder.image;

    }



    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }


    public String getName() {
        return name;
    }

    public ArrayList<String> getContactInfo() {
        return contactInfo;
    }

    public String getMailaddress() {
        return mailaddress;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public String getHotelID() {
        return hotelID;
    }

    public String getImage() {
        return image;
    }






    public static  class hotelBuilder{

        private String hotelID;
        private String name;
        private ArrayList<String> contactInfo;
        private String mailaddress;
        //private List<HotelRoomClass> hotelRoom;
        private String websiteLink;
        private String country;
        private String city;
        private String address;
        private String image;


        public hotelBuilder setCountry(String country) {

            this.country = country;
            return this;

        }

        public hotelBuilder setImage(String image) {
            this.image = image;
            return this;

        }

        public hotelBuilder setCity(String city) {

            this.city = city;
            return this;

        }

        public hotelBuilder setAddress(String address) {

            this.address = address;
            return this;

        }

        public hotelBuilder setName(String name) {

            this.name = name;
            return this;

        }

        public hotelBuilder setContactInfo(ArrayList<String> contactInfo) {
            this.contactInfo = contactInfo;
            return this;

        }

        public hotelBuilder setMailaddress(String mailaddress) {

            this.mailaddress = mailaddress;
            return this;

        }

        public hotelBuilder setHotelID(String hotelID) {

            this.hotelID = hotelID;
            return this;

        }

        public hotelBuilder setWebsiteLink(String websiteLink) {

            this.websiteLink = websiteLink;
            return this;

        }

        public HotelClass build() {
            HotelClass hotelClass = new HotelClass(this);
            return hotelClass;
        }



    }



//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//    public void setImage(String image) { this.image = image; }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setContactInfo(ArrayList<String> contactInfo) {
//        this.contactInfo = contactInfo;
//    }
//
//    public void setMailaddress(String mailaddress) {
//        this.mailaddress = mailaddress;
//    }
//
//    public void setHotelID(String hotelID) {
//        this.hotelID = hotelID;
//    }
//
//    public void setWebsiteLink(String websiteLink) {
//        this.websiteLink = websiteLink;
//    }


}
