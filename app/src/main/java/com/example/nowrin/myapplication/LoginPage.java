package com.example.nowrin.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginPage extends AppCompatActivity {
    private Button button_signup;
    private Button button_log;
    private EditText loinuser;
    private EditText loginpass;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    AuthenticationSingleTone auth;
    protected void setId()
    {
        loginpass=(EditText)findViewById(R.id.log_pass);
        loinuser=(EditText)findViewById(R.id.log_user);
        button_log=(Button)findViewById(R.id.loginButton);
        button_signup=(Button)findViewById(R.id.signupbutton);
    }
    protected  void check()
    {
        //firebaseAuth=FirebaseAuth.getInstance();
        auth=AuthenticationSingleTone.getInstance();
        firebaseAuth = auth.firebaseAuth;
        firebaseDatabase=FirebaseDatabase.getInstance();
        final String usern=loinuser.getText().toString().trim();
        final  String pas=loginpass.getText().toString().trim();
        User user;
        user = new User.UserBuilder().
                setUsername(usern)
                .setPassword(pas).build();
        final ProgressDialog progressDialog=ProgressDialog.show(LoginPage.this,"Please wait...","Processing",true);
        (firebaseAuth.signInWithEmailAndPassword(user.getUsername(),user.getPassword())).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful())
                        {
                            Toast.makeText(LoginPage.this,"Login Successful!!!",Toast.LENGTH_LONG).show();
                            Intent n = new Intent(getApplication(), Profile.class);
                            startActivity(n);
                        }
                        else
                        {
                            Log.e("ERROR",task.getException().toString());
                            Toast.makeText(LoginPage.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                        }

                    }
                });




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        setId();
        button_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(getApplication(), SignUpJava.class);
                startActivity(n);

            }
        });
    }
}
