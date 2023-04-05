package com.example.nowrin.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class hotel_profile extends AppCompatActivity {

    int flag;

    Button editNotify;
    TextView hotelName,emailView,weblink,address;
    ImageView cover_image;
    ListView contactList;

    String hotelID;
     ArrayList<String> phoneNoList;
     ArrayAdapter<String> phoneNoadapter;


    //Firebase
    private DatabaseReference mdatabasereference;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_profile);


        Bundle bundle = getIntent().getExtras();
        hotelID = bundle.getString("HOTELID");

        flag =0;



        hotelName = (TextView) findViewById(R.id.hotelName);
        emailView = (TextView) findViewById(R.id.emailView);
        weblink = (TextView) findViewById(R.id.web_link);
        address = (TextView) findViewById(R.id.addressView);

        editNotify = (Button) findViewById(R.id.EditButton);

        cover_image = (ImageView) findViewById(R.id.imgView2);


        contactList = (ListView)findViewById(R.id.contact_list);
        phoneNoList = new ArrayList<String>();
        contactList = (ListView)findViewById(R.id.contactList);



        //Firebase
        context = new Context(new appHotelFirebaseReference());
        mdatabasereference = context.getDBreference();


        //
        Query query = mdatabasereference.orderByChild("hotelID").equalTo(hotelID);
        query.addListenerForSingleValueEvent(valueEventListener);



        if(FirebaseAuth.getInstance().getCurrentUser()!=null){

            editNotify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(hotel_profile.this,FullInformation.class);
                    startActivity(i);
                }
            });

        }else{

            editNotify.setVisibility(View.GONE);
        }


    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            //artistList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HotelClass hotelClass = snapshot.getValue(HotelClass.class);
                    //artistList.add(artist);
                    setDetails(hotelClass);
                }

            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    public void setDetails(HotelClass hotelClass){


        if(hotelClass.getName()!=null){

            String n= hotelClass.getName();
            //System.out.println("*****"+n+"*****");
            hotelName.setText(n);

        }
        if(hotelClass.getMailaddress()!=null){

            emailView.setText(hotelClass.getMailaddress());

        }
        if(hotelClass.getWebsiteLink()!=null){
            weblink.setText(hotelClass.getWebsiteLink());

        }
        if(hotelClass.getAddress()!=null){
            address.setText(hotelClass.getAddress()+" "+hotelClass.getCountry());

        }

        if(!(hotelClass.getContactInfo().isEmpty())){

            phoneNoList = hotelClass.getContactInfo();

            System.out.println(phoneNoList);
           // phoneNoadapter = new ArrayAdapter(this,R.layout.contact_listview_item,R.id.phoneNo,phoneNoList);
            //contactList.setAdapter(phoneNoadapter);

        }


        String img = hotelClass.getImage();

        if(img!=null){
            Glide.with(getApplicationContext()).load(img).into(cover_image);

        }


    }


}
