package com.example.nowrin.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminSignUp extends AppCompatActivity {
    private Button signup;
    private EditText code;
    private EditText pass;
    private EditText confirmpass;
//    private FirebaseAuth firebaseAuth;
//    DatabaseReference databaseReference;
//    AuthenticationSingleTone auth;

//    protected void connect_to_firebase() {
//        databaseReference = FirebaseDatabase.getInstance().getReference("users");
//
//
//    }

    protected void setId() {
        signup = (Button) findViewById(R.id.regi_button);
        code = (EditText) findViewById(R.id.code);
        pass = (EditText) findViewById(R.id.regi_pass);
        confirmpass = (EditText) findViewById(R.id.regi_con_pass);
    }
    protected void adduser()
    {
        final String codeValue=code.getText().toString().trim();
        final  String pas=pass.getText().toString().trim();

        //final String phon=phone.getText().toString().trim();
        final String con=confirmpass.getText().toString().trim();
        if(!TextUtils.isEmpty(codeValue) && !TextUtils.isEmpty(pas) && !TextUtils.isEmpty(con) )
        {

            if(!pas.equals(con))
            {
                Toast.makeText(AdminSignUp.this, "Password Not Matched", Toast.LENGTH_LONG).show();

            }

            else {

                Intent i = new Intent(AdminSignUp.this,AddHotel.class);
                i.putExtra("code",codeValue);
                i.putExtra("password",pas);
                startActivity(i);




            }

        }
        else
        {
            Toast.makeText(this,"Some field missing",Toast.LENGTH_LONG).show();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_up);

        setId();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adduser();
            }
        });
    }
}
