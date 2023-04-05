package com.example.nowrin.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    AuthenticationSingleTone auth;
    FirebaseUser user;
    TextView t;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        auth=AuthenticationSingleTone.getInstance();
        firebaseAuth = auth.firebaseAuth;
        user=firebaseAuth.getCurrentUser();
        b1=(Button)findViewById(R.id.logout);
        t=findViewById(R.id.textView3);
        t.setText(user.getEmail());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout();
            }
        });
    }
    public void signout()
    {firebaseAuth.signOut();
    finish();
    Intent x=new Intent(Profile.this,MainActivity.class);
    startActivity(x);}
}
