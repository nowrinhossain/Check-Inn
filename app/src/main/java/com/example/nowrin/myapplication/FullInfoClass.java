package com.example.nowrin.myapplication;

public class FullInfoClass {
    String name,phone,type,dateF,dateE,email;
    Boolean result,review;

    public String getName() {
        return name;
    }
    public Boolean getResult()
    {
        return result;
    }
    public Boolean getReview()
    {
        return review;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getType() {
        return type;
    }

    public String getDateF() {
        return dateF;
    }

    public String getDateE() {
        return dateE;
    }



    public FullInfoClass(String name,String email,Boolean result,Boolean review, String phone, String type, String dateFrom, String dateTo) {
        this.name = name;
        this.phone = phone;
        this.email= email;
        this.type = type;
        this.dateF = dateFrom;
        this.dateE = dateTo;
        this.result=result;
        this.review=review;
    }

    public FullInfoClass() {

    }
}
