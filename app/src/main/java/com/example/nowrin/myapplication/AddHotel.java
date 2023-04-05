package com.example.nowrin.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;


public class AddHotel extends AppCompatActivity {

    private ArrayList<String>phoneNoList;
    private ArrayAdapter<String>phoneNoadapter;
    private EditText phoneNoInput;

    private Button btnChoose, btnUpload, btnRequest,btnphnNoSave;
    private ImageView imageView;
    private TextView mail_textView,contact_textView,phn_textView,website_textView ;
    private EditText hotelName,mail_account,web_link,country,City,address;
    private ListView phnNoListView;


    private Uri filePath;
    private Uri downloadUrl;
    private final int PICK_IMAGE_REQUEST = 71;


    //Firebase
    Context context;
    DatabaseReference databaseHotels,adminDatabaseReference;
   // FirebaseStorage storage;
    StorageReference firebaseStorageReference;

    firebaseStorageSingleton fbaseStorage;


    String hotelCode;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        btnRequest = (Button) findViewById(R.id.request_button);
        btnphnNoSave = (Button) findViewById(R.id.phnNoSave_btn);

        imageView = (ImageView) findViewById(R.id.imgView);

        hotelName = (EditText) findViewById(R.id.hotel_name);
        address = (EditText) findViewById(R.id.address);
        City = (EditText) findViewById(R.id.City);
        country = (EditText) findViewById(R.id.country);
        mail_account = (EditText) findViewById(R.id.mail_editText);
        web_link = (EditText) findViewById(R.id.websiteLink);
        phoneNoInput = (EditText) findViewById(R.id.contactInput);


        phoneNoList = new ArrayList<String>();
        phnNoListView = (ListView)findViewById(R.id.contactList);
        phoneNoadapter = new ArrayAdapter<String>(this,R.layout.contact_listview_item,R.id.phoneNo,phoneNoList);
        phnNoListView.setAdapter(phoneNoadapter);


        Bundle bundle = getIntent().getExtras();
        hotelCode = bundle.getString("code");
        password = bundle.getString("password");

        adminDatabaseReference = FirebaseDatabase.getInstance().getReference("Registered_admin");




        fbaseStorage = firebaseStorageSingleton.getInstance();
        firebaseStorageReference = fbaseStorage.storageReference;


        context = new Context(new appHotelFirebaseReference());
        databaseHotels = context.getDBreference();


        btnphnNoSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String phnNo = phoneNoInput.getText().toString();
                phoneNoList.add(phnNo);
                phoneNoadapter.notifyDataSetChanged();

            }
        });

    }




    public void onClick(View v) {

        chooseImage();
    }

    public void onClick2(View v) {
        //Toast.makeText(AddHotel.this, "Click me",Toast.LENGTH_LONG).show();
        //Intent intent = new Intent(AddHotel.this, HomePage.class);
        //startActivity(intent);

        uploadImage();
    }


    public void onClick3(View v){

        Toast.makeText(AddHotel.this, "******",Toast.LENGTH_SHORT).show();
        SaveHotelEntity();
    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = firebaseStorageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            downloadUrl = taskSnapshot.getDownloadUrl();
                            Toast.makeText(AddHotel.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddHotel.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }


    private void SaveHotelEntity(){

        //Toast.makeText(AddHotel.this, "Data Saved",Toast.LENGTH_SHORT).show();

        String name = hotelName.getText().toString();
        String link = web_link.getText().toString();
        String email = mail_account.getText().toString();
        String city = City.getText().toString();
        String add = address.getText().toString();
        ArrayList<String>arrList = new ArrayList<>();
        String imageurl=null;

        String id = databaseHotels.push().getKey();

        HotelClass hotelClass;

        int flag=0;


        if(!phoneNoList.isEmpty()){
            arrList = phoneNoList;
        }
        if (downloadUrl != null && !downloadUrl.equals(Uri.EMPTY)) {
             imageurl = downloadUrl.toString();
        }


        if(name==null  || city==null || add==null || arrList.isEmpty() || imageurl==null){

            Toast.makeText(AddHotel.this, "Please , Fill up the required fields",Toast.LENGTH_SHORT).show();
            if(imageurl==null){
                Toast.makeText(AddHotel.this, "Upload and save an image",Toast.LENGTH_SHORT).show();

            }

        }
        else if(link!=null){

             hotelClass = new HotelClass.hotelBuilder()
                    .setHotelID(id)
                    .setName(name)
                    .setAddress(add)
                    .setCity(city)
                    .setMailaddress(email)
                    .setWebsiteLink(link)
                    .build();
            databaseHotels.child(id).setValue(hotelClass);
            Toast.makeText(AddHotel.this, "Request Accepted",Toast.LENGTH_SHORT).show();

            flag =1;
        }

        else {

            hotelClass = new HotelClass.hotelBuilder()
                    .setHotelID(id)
                    .setName(name)
                    .setAddress(add)
                    .setCity(city)
                    .setMailaddress(email)
                    .build();

            databaseHotels.child(id).setValue(hotelClass);
            Toast.makeText(AddHotel.this, "Request Accepted",Toast.LENGTH_SHORT).show();

            flag=1;
        }


        if(flag==1){
            String id2 = adminDatabaseReference.push().getKey();
            AdminClass adminClass = new AdminClass();

            adminClass.setCode(hotelCode);
            adminClass.setPassword(password);
            adminClass.setHotelID(id);
            adminClass.setOwnID(id2);

            adminDatabaseReference.child(id2).setValue(adminClass);

            Intent i = new Intent(AddHotel.this,HomePage.class);
            startActivity(i);



        }






    }





}
