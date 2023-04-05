package com.example.nowrin.myapplication;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class firebaseStorageSingleton {


    private static firebaseStorageSingleton instance = new firebaseStorageSingleton();
    private FirebaseStorage storage;
    public StorageReference storageReference;


    private firebaseStorageSingleton(){
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }
    public static firebaseStorageSingleton getInstance(){
        return instance;
    }
}
