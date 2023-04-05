package com.example.nowrin.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class popularCityInput extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_city_input);

        DatabaseReference databasePlaces;






        databasePlaces = FirebaseDatabase.getInstance().getReference("PopularPlaces");



        String id = databasePlaces.push().getKey();


        PopularPlaces p =new PopularPlaces();
        p.setCity("Cox's Bazar");
        p.setImage("https://firebasestorage.googleapis.com/v0/b/sdplab22.appspot.com/o/cityImage%2FCox's_Bazar.jpeg?alt=media&token=54dce97b-48ba-4085-9a23-740560e8dd51");

        p.setID(id);

        databasePlaces.child(id).setValue(p);


        String id1 = databasePlaces.push().getKey();
        PopularPlaces p1 =new PopularPlaces();
        p1.setCity("Bandarban");
        p1.setImage("https://firebasestorage.googleapis.com/v0/b/sdplab22.appspot.com/o/cityImage%2Fbandarban.jpg?alt=media&token=3857c592-7ab3-41f2-b739-097077ded5b3");
        p.setID(id1);

        databasePlaces.child(id1).setValue(p1);



        String id3 = databasePlaces.push().getKey();
        PopularPlaces p3 =new PopularPlaces();
        p3.setCity("Sylhet");
        p3.setImage("https://firebasestorage.googleapis.com/v0/b/sdplab22.appspot.com/o/cityImage%2Fsylhet.jpg?alt=media&token=e5783e9a-ee1c-4f06-a54e-9487c031b0ae");
        p.setID(id3);

        databasePlaces.child(id3).setValue(p3);



        String id4 = databasePlaces.push().getKey();
        PopularPlaces p4 =new PopularPlaces();
        p4.setCity("Sajek Valley");
        p4.setImage("https://firebasestorage.googleapis.com/v0/b/sdplab22.appspot.com/o/cityImage%2Fsajek.jpeg?alt=media&token=e1d29468-d75f-4f0d-9bcd-d0b1c0e76c83");
        p4.setID(id4);

        databasePlaces.child(id4).setValue(p4);


        String id6 = databasePlaces.push().getKey();
        PopularPlaces p6 =new PopularPlaces();
        p6.setCity("Saint Martin");
        p6.setImage("https://firebasestorage.googleapis.com/v0/b/sdplab22.appspot.com/o/cityImage%2Fsaint_martin.jpeg?alt=media&token=132f48d3-7184-4cde-bece-889d82a93008");
        p6.setID(id6);

        databasePlaces.child(id6).setValue(p6);


        String id7 = databasePlaces.push().getKey();
        PopularPlaces p7 =new PopularPlaces();
        p7.setCity("Sundarban");
        p7.setImage("https://firebasestorage.googleapis.com/v0/b/sdplab22.appspot.com/o/cityImage%2Fsundarban.jpg?alt=media&token=34ffb1bd-5243-47cd-87de-2869cfe0fb2b");
        p7.setID(id7);

        databasePlaces.child(id7).setValue(p7);





        String id5 = databasePlaces.push().getKey();
        PopularPlaces p5 =new PopularPlaces();
        p5.setCity("Rajshahi");
        p5.setImage("https://firebasestorage.googleapis.com/v0/b/sdplab22.appspot.com/o/cityImage%2Frajshahi.jpeg?alt=media&token=13751b32-4e18-4032-8f05-409faa534b92");
        p5.setID(id5);

        databasePlaces.child(id5).setValue(p5);












    }
}
