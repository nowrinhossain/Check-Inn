package com.example.nowrin.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.CALL_PHONE;
import android.Manifest;

public class ShowNotification extends AppCompatActivity {

    DatabaseReference databaseArtists;
    ListView listViewArtists;
    List<FullInfoClass> artistList;
    EditText editText1;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notification);

        Bundle bundle = getIntent().getExtras();


        databaseArtists = FirebaseDatabase.getInstance().getReference("BookInformation");
        listViewArtists = (ListView) findViewById(R.id.listViewNotification);
        artistList = new ArrayList<>();
        //editText1=(EditText) findViewById(R.id.editText2);


    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
        super.onStart();
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                artistList.clear();
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    FullInfoClass artist = artistSnapshot.getValue(FullInfoClass.class);
                    artistList.add(artist);
                }
                listViewOfNotification adapter = new listViewOfNotification(ShowNotification.this, artistList);
                listViewArtists.setAdapter(adapter);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public class listViewOfNotification extends ArrayAdapter<FullInfoClass> {
        private Activity context;
        private List<FullInfoClass> infolist;

        public listViewOfNotification(Activity context, List<FullInfoClass> infoList) {
            super(context, R.layout.activity_list_view_of_notification, infoList);
            this.context = context;
            this.infolist = infoList;
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.activity_list_view_of_notification, null, true);
            TextView textViewName = (TextView) listViewItem.findViewById(R.id.fullString);
            Button acceptb=listViewItem.findViewById(R.id.acceptButton);
            Button declineb=listViewItem.findViewById(R.id.declineButton);




            final FullInfoClass artist = infolist.get(position);
            textViewName.setText(artist.getName()+" wants to book "+artist.getType()+
                    " type room from "+artist.getDateF()+" to " +artist.getDateE()+
                    " . Contact info - "+artist.getPhone()+" Email No. - "+artist.getEmail()+" . Please contact with the receipent to accept." );
            final String call ="tel:"+artist.getPhone();

            acceptb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);

                    callIntent.setData(Uri.parse(call));



                    startActivity(callIntent);
                    // Intent i = new Intent(getContext(),Profile.class);
                     //Toast.makeText()
                    //startActivity(i);

                }
            });


            return listViewItem;
        }

    }




    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // permission was granted, yay! Do the
//                    // contacts-related task you need to do.
//                } else {
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request.
//        }
    }

}
