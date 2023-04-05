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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class HomePage extends AppCompatActivity {


    private RecyclerView popularPlaceList;
    SearchView searchView;
    private Button searchbutton,mapButton,userSignButton,adminSignButton;
    public String DESTINATION ;

    //Firebase
    Context context;
    private DatabaseReference mDatabaseReference;
    FirebaseRecyclerAdapter<PopularPlaces,popularPlaceViewHolder>firebaseRecyclerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        popularPlaceList = (RecyclerView) findViewById(R.id.popularPlaceRecycler);
        searchbutton = (Button) findViewById(R.id.my_search_button);
        mapButton = (Button) findViewById(R.id.goToMap);
        userSignButton = (Button) findViewById(R.id.user_signbtn);
        adminSignButton = (Button) findViewById(R.id.admin_signbtn);


        if(FirebaseAuth.getInstance().getCurrentUser()!=null){

            userSignButton.setText("Profile");
        }



        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        //RecyclerView myList = (RecyclerView) findViewById(R.id.my_recycler_view);
        popularPlaceList.setLayoutManager(layoutManager);


        //popularPlaceList.setLayoutManager(new LinearLayoutManager(this));

        context = new Context(new appPopularDestinationFirebaseReference());
        mDatabaseReference =context.getDBreference();
        //mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("PopularPlaces");
        mDatabaseReference.keepSynced(true);

        final Query roomQuery = mDatabaseReference.orderByKey();

        FirebaseRecyclerOptions roomOptions = new FirebaseRecyclerOptions.Builder<PopularPlaces>().setQuery(roomQuery, PopularPlaces.class).build();
        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<PopularPlaces,popularPlaceViewHolder >(roomOptions) {
            @Override
            protected void onBindViewHolder(@NonNull popularPlaceViewHolder holder, int position, @NonNull final PopularPlaces model) {

                //System.out.println("*******************");

                holder.setDetails(model);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(v.getContext(), position + "", Toast.LENGTH_SHORT).show();

                        DESTINATION = model.getCity();


                        System.out.println("DEStination "+DESTINATION);
                        Intent i = new Intent(HomePage.this,HotelSearchActivity.class);
                        i.putExtra("cityName",DESTINATION);
                        startActivity(i);


                    }
                });

            }

            @NonNull
            @Override
            public popularPlaceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.popular_places_cardview, viewGroup, false);

                return new popularPlaceViewHolder(view);            }


        };

        popularPlaceList.setAdapter(firebaseRecyclerAdapter);

        searchView = (SearchView) findViewById(R.id.destination_search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                //System.out.println("QUERY"+query);

                if (query!=null)
                {

                    Toast.makeText(HomePage.this, "text entry found", Toast.LENGTH_SHORT).show();
                    searchbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DESTINATION = query;

                            System.out.println("DESTINATION"+DESTINATION);


                            Intent i = new Intent(HomePage.this,HotelSearchActivity.class);
                            i.putExtra("cityName",DESTINATION);
                            startActivity(i);
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



    }

    public void gotomap(View v){
        Intent i=new Intent(HomePage.this,MapsActivity.class);
        startActivity(i);


    }
    public void gotousersignup(View v){

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent i=new Intent(HomePage.this,Profile.class);
            startActivity(i);

        }else{

            Intent i=new Intent(HomePage.this,LoginPage.class);
            startActivity(i);
        }


    }
    public void gotoadminsignup(View v){

        Intent i = new Intent(HomePage.this,AdminLogin.class);
        startActivity(i);
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


    public class popularPlaceViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public popularPlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setDetails(PopularPlaces popularPlaces){

            ImageView iv = (ImageView) mView.findViewById(R.id.iv);
            TextView tv = (TextView) mView.findViewById(R.id.tv);

            String img = popularPlaces.getImage();

            System.out.println(popularPlaces.getCity());

            tv.setText(popularPlaces.getCity());
            Glide.with(getApplicationContext()).load(img).into(iv);


            //System.out.println("**********"+hotelRoomClass.getRoomStatus()+"**********");

        }
    }



}
