package com.example.nowrin.myapplication;

import com.google.firebase.database.DatabaseReference;

public class Context {

    private appFirebaseReference firebaseReference;

    public Context(appFirebaseReference firebaseReference){

        this.firebaseReference = firebaseReference;
    }

    public DatabaseReference getDBreference(){

        return firebaseReference.getDatabaseReference();
    }
}
