package com.example.nowrin.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AdminLogin extends AppCompatActivity {

    String codeNumber;
    String passWord;
    int codeInteger;

    EditText codeValue;
    EditText pass;
    Button login;
    Button signup;

    DatabaseReference adminDatabasereference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        codeValue =findViewById(R.id.log_user);
        pass =findViewById(R.id.log_pass);
        login =findViewById(R.id.loginButton);
        signup =findViewById(R.id.signupbutton);

         codeNumber= codeValue.getText().toString().trim();
         passWord= pass.getText().toString().trim();
        //codeInteger = Integer.parseInt(codeNumber);

        adminDatabasereference = FirebaseDatabase.getInstance().getReference("Registered_admin");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Query query = adminDatabasereference.orderByChild("codeNumber").equalTo(codeNumber);
                query.addListenerForSingleValueEvent(valueEventListener);
                Toast.makeText(AdminLogin.this, "Login Failed", Toast.LENGTH_SHORT).show();



                Intent i = new Intent(AdminLogin.this,ShowNotification.class);
                i.putExtra("Code",codeNumber);
                startActivity(i);

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminLogin.this,AdminSignUp.class);
                startActivity(i);
            }
        });



    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            //artistList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AdminClass adminClass = snapshot.getValue(AdminClass.class);
                    //artistList.add(artist);
                    check(adminClass);
                }

            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    public void check(AdminClass adminClass){


        if(adminClass.getPassword().equals( passWord)){

            Intent i = new Intent(AdminLogin.this,ShowNotification.class);
            i.putExtra("Code",codeNumber);
            startActivity(i);
        }
        else {
            Toast.makeText(AdminLogin.this, "Login Failed", Toast.LENGTH_SHORT).show();

        }
    }

}
