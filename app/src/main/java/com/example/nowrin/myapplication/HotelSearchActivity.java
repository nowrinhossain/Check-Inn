package com.example.nowrin.myapplication;

import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;


public class HotelSearchActivity extends AppCompatActivity {


    private String DESTINATION;
    private String cityName;

    private RecyclerView hotelList;
    private SearchView searchView;
    private Button searchbtn;

    //Firebase
    private Context contexHotel;
    private DatabaseReference mDatabaseReference;
    private FirebaseRecyclerAdapter<HotelClass,roomViewHolder>firebaseRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_activity);

        //Intent intent = getIntent();

        searchView = (SearchView) findViewById(R.id.destination_search);
        searchbtn = (Button) findViewById(R.id.searchbtn);


        hotelList = (RecyclerView) findViewById(R.id.hotelPlaceRecycler);
        hotelList.setLayoutManager(new LinearLayoutManager(this));



        Bundle bundle = getIntent().getExtras();
        cityName = bundle.getString("cityName");
        System.out.println("City Name "+cityName);

        contexHotel = new Context(new appHotelFirebaseReference());
        mDatabaseReference = contexHotel.getDBreference();

        databaseQuery();
//        firebaseRecyclerAdapter.startListening();


//        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Hotels");




        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                //System.out.println("QUERY"+query);

                if (query!=null)
                {

                    searchbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cityName = query;
                            databaseQuery();
                            //firebaseRecyclerAdapter.startListening();


                        }
                    });

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        //databaseReference = FirebaseDatabase.getInstance().getReference("Hotels").child(intent.getStringExtra())



    }

    private void databaseQuery(){


        Query query = mDatabaseReference.orderByChild("city")
                .equalTo(cityName);

        FirebaseRecyclerOptions roomOptions = new FirebaseRecyclerOptions.Builder<HotelClass>().setQuery(query, HotelClass.class).build();
        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<HotelClass, roomViewHolder>(roomOptions) {
            @Override
            protected void onBindViewHolder(@NonNull roomViewHolder holder, int position, @NonNull HotelClass model) {

                holder.setDetails(model);
                System.out.println("##########"+model.getName());

            }

            @NonNull
            @Override
            public roomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.hotel_cardview, viewGroup, false);

                return new roomViewHolder(view);            }


        };

        hotelList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();


    }




    @Override
    public void onStart() {
        super.onStart();
        //databaseQuery();
        //firebaseRecyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        //firebaseRecyclerAdapter.stopListening();
    }





    public  class roomViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public roomViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setDetails(HotelClass hotelClass){
            ImageView img = (ImageView) mView.findViewById(R.id.hotelImage);
            TextView hotelName = (TextView) mView.findViewById(R.id.hotelName);
            Button viewDetails = (Button) mView.findViewById(R.id.viewDetails);



            hotelName.setText(hotelClass.getName());
            String imgLink = hotelClass.getImage();
            Glide.with(getApplicationContext()).load(imgLink).into(img);

            final String hotelID = hotelClass.getHotelID();

            viewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(HotelSearchActivity.this,hotelRoomsActivity.class);
                    i.putExtra("HOTELID",hotelID);
                    startActivity(i);

                }
            });


        }
    }

}
