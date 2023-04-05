package com.example.nowrin.myapplication;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class appHotelFirebaseReference implements appFirebaseReference {


    public DatabaseReference getDatabaseReference(){
        return  FirebaseDatabase.getInstance().getReference("Hotels");
    }

}
