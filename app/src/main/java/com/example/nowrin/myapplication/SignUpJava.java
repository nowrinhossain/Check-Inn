package com.example.nowrin.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpJava extends AppCompatActivity {

    private Button signup;
    private EditText username;
    private EditText pass;
    private EditText email;
    private EditText confirmpass;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    AuthenticationSingleTone auth;

    protected void connect_to_firebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("users");


    }

    protected void setId() {
        signup = (Button) findViewById(R.id.regi_button);
        username = (EditText) findViewById(R.id.regi_user);
        pass = (EditText) findViewById(R.id.regi_pass);
        email = (EditText) findViewById(R.id.regi_email);
        confirmpass = (EditText) findViewById(R.id.regi_con_pass);
    }
    protected void adduser()
    {
        final String usern=username.getText().toString().trim();
        final  String pas=pass.getText().toString().trim();
        final String mail=email.getText().toString().trim();
        //final String phon=phone.getText().toString().trim();
        final String con=confirmpass.getText().toString().trim();
        if(!TextUtils.isEmpty(usern) && !TextUtils.isEmpty(pas) && !TextUtils.isEmpty(mail) )
        {

             if(!pas.equals(con))
            {
                Toast.makeText(SignUpJava.this, "Password Not Matched", Toast.LENGTH_LONG).show();

            }

            else {
                final ProgressDialog progressDialog=ProgressDialog.show(SignUpJava.this,"Please wait...","Processing",true);
                (firebaseAuth.createUserWithEmailAndPassword(mail,pas)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()) {
                            addEmailVerification();

                        }
                        else
                        {
                            Log.e("ERROR",task.getException().toString());
                            Toast.makeText(SignUpJava.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });



            }

        }
        else
        {
            Toast.makeText(this,"Some field missing",Toast.LENGTH_LONG).show();
        }

    }

    private void addEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(SignUpJava.this,"Verify your email",Toast.LENGTH_LONG).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpJava.this,"Verification Failed",Toast.LENGTH_LONG).show();


            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_java);
       auth=AuthenticationSingleTone.getInstance();
        firebaseAuth = auth.firebaseAuth;


        setId();
        connect_to_firebase();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adduser();
            }
        });
    }
}
