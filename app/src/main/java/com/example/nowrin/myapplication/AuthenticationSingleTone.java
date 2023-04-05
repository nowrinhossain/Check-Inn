package com.example.nowrin.myapplication;

import com.google.firebase.auth.FirebaseAuth;

public class AuthenticationSingleTone {
    public FirebaseAuth firebaseAuth;
    private static AuthenticationSingleTone instance = new AuthenticationSingleTone();
    private AuthenticationSingleTone(){
        firebaseAuth =FirebaseAuth.getInstance();

    }
    public static AuthenticationSingleTone getInstance(){
        return instance;
    }

}
