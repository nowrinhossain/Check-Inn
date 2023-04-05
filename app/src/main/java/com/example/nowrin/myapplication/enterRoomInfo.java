package com.example.nowrin.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class enterRoomInfo extends AppCompatActivity {


    EditText numberOfBeds,adultCapacity,childCapacity,Cost,roomStatus,extraAdvantages;
    Button saveRoomInfo;

    //Firebase;
    Context context;
    DatabaseReference databaseHotelRoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_room_info);

        numberOfBeds = (EditText) findViewById(R.id.numberOfBedsInput);
        adultCapacity = (EditText) findViewById(R.id.adultCapacityInput);
        childCapacity = (EditText) findViewById(R.id.childCapacityInput);
        Cost = (EditText) findViewById(R.id.costInput);
        roomStatus = (EditText) findViewById(R.id.inputStatus);
        extraAdvantages = (EditText)findViewById(R.id.ExtraAdvantageInput);

        saveRoomInfo = (Button) findViewById(R.id.saveRoomInfo);


        //Firebase
        context = new Context(new appHotelRoomsFirebaseReference());
        databaseHotelRoom = context.getDBreference();

//        databaseHotelRoom = FirebaseDatabase.getInstance().getReference("HotelRooms");
        //databaseHotelRoom =



    }

    public void OnClick(View v){

        SaveRoomData();
    }




    private void SaveRoomData(){


        HotelRoomClass hotelRoomClass = new HotelRoomClass();

        if(numberOfBeds.getText().toString()!=null){

            int a= Integer.parseInt(numberOfBeds.getText().toString());

            hotelRoomClass.setBednumbers(a);
        }
        if(adultCapacity.getText().toString()!=null){
            int b = Integer.parseInt(adultCapacity.getText().toString());
            hotelRoomClass.setAdult_guestnumber(b);
        }
        if(childCapacity.getText().toString()!=null){

            int c = Integer.parseInt(childCapacity.getText().toString());
            hotelRoomClass.setChild_guestnumber(c);
        }
        if(Cost.getText().toString()!=null){

            double d = Double.parseDouble(Cost.getText().toString());
            hotelRoomClass.setCost(d);
        }
        if(roomStatus.getText().toString()!=null){

            hotelRoomClass.setRoomStatus(roomStatus.getText().toString());
        }
        if(extraAdvantages.getText().toString()!=null){

            hotelRoomClass.setExtraAdvantage(extraAdvantages.getText().toString());
        }

        saveInFirebase(hotelRoomClass);



    }


    private void saveInFirebase(HotelRoomClass h){

        String id = databaseHotelRoom.push().getKey();

        h.setThisRoomID(id);

        databaseHotelRoom.child(id).setValue(h);


    }
}
