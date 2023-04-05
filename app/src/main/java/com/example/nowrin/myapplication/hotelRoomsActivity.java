package com.example.nowrin.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class hotelRoomsActivity extends AppCompatActivity {

    private ImageView cover_image;
    private TextView hotelName;
    private RecyclerView hotelRoomList;

    String hotelID;


    //Firebase
    private Context contextHotel,contextHotelRoom;
    private DatabaseReference hotelDatabaseReference,hotelRoomDatabaseReference;
    FirebaseRecyclerAdapter<HotelRoomClass,roomViewHolder>firebaseRecyclerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_rooms);

        Bundle bundle = getIntent().getExtras();
        hotelID = bundle.getString("HOTELID");



        cover_image = (ImageView) findViewById(R.id.coverImage);
        hotelName = (TextView) findViewById(R.id.hotelName);

        hotelRoomList = (RecyclerView) findViewById(R.id.roomRecyclerView);
        hotelRoomList.setLayoutManager(new LinearLayoutManager(this));


        //mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("HotelRooms");
        //mDatabaseReference.keepSynced(true);



        contextHotel = new Context(new appHotelFirebaseReference());
        hotelDatabaseReference = contextHotel.getDBreference();
        Query query = hotelDatabaseReference.orderByChild("hotelID").equalTo(hotelID);
        query.addListenerForSingleValueEvent(valueEventListener);




        contextHotelRoom = new Context(new appHotelRoomsFirebaseReference());
        hotelRoomDatabaseReference = contextHotelRoom.getDBreference();
        Query roomQuery = hotelRoomDatabaseReference.orderByChild("hotelID")
                .equalTo(hotelID);


        FirebaseRecyclerOptions roomOptions = new FirebaseRecyclerOptions.Builder<HotelRoomClass>().setQuery(roomQuery, HotelRoomClass.class).build();
        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<HotelRoomClass, roomViewHolder>(roomOptions) {
            @Override
            protected void onBindViewHolder(@NonNull roomViewHolder holder, int position, @NonNull HotelRoomClass model) {

                //System.out.println("**********"+model.getRoomStatus()+"**********");

                holder.setDetails(model);
            }

            @NonNull
            @Override
            public roomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.room_of_a_hotel, viewGroup, false);

                return new roomViewHolder(view);            }


        };

        hotelRoomList.setAdapter(firebaseRecyclerAdapter);

    }


    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();

    }

    public void viewHotelInfo(View v){

        Intent intent = new Intent(hotelRoomsActivity.this, hotel_profile.class);
        intent.putExtra("HOTELID",hotelID);
        startActivity(intent);

    }




    public static class roomViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public roomViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setDetails(HotelRoomClass hotelRoomClass){
            TextView status = (TextView) mView.findViewById(R.id.roomStatus);
            TextView extraAdvantage = (TextView) mView.findViewById(R.id.extraAdvantage);
            TextView bedNumbers = (TextView) mView.findViewById(R.id.bedNumbers);
            TextView guestCapacity = (TextView) mView.findViewById(R.id.guestCapacity);
            TextView roomCost = (TextView) mView.findViewById(R.id.roomCost);

            status.setText(hotelRoomClass.getRoomStatus());
            extraAdvantage.setText(hotelRoomClass.getExtraAdvantage());
            bedNumbers.setText(String.valueOf(hotelRoomClass.getBednumbers())+" Beds");
            guestCapacity.setText("Capacity: "+String.valueOf(hotelRoomClass.getAdult_guestnumber())+" number of adults");
            roomCost.setText(String.valueOf(hotelRoomClass.getCost())+"/=" );


        }
    }



    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HotelClass hotelClass = snapshot.getValue(HotelClass.class);
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
            hotelName.setText(n);

        }
        if(hotelClass.getImage()!=null){

            String img = hotelClass.getImage();
            Glide.with(getApplicationContext()).load(img).into(cover_image);

        }


    }

}
