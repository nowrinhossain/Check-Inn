package com.example.nowrin.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class FullInformation extends AppCompatActivity {
    private Button mFirebutton;
    private DatabaseReference mdatabase;
    List<FullInfoClass> fullInfo;
    FirebaseAuth fAuth;
    FirebaseUser user;

    private EditText mname;
    private EditText mPhone;
    private EditText mType;
    private EditText mDateFrom;
    private EditText mDateTo;
    private Button setdatefrom;
    private Button setdateTo;
    String datef1;
    String datet1;
    Calendar cal= Calendar.getInstance();
    DateFormat formatDate = DateFormat.getDateInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_information);

        mFirebutton = (Button)findViewById(R.id.book_now);
        mdatabase = FirebaseDatabase.getInstance().getReference();
        fAuth=FirebaseAuth.getInstance();
        user=fAuth.getCurrentUser();

        mname= (EditText)findViewById(R.id.book_name);
        mPhone= (EditText)findViewById(R.id.book_phone);
        mType= (EditText)findViewById(R.id.book_type);
        mDateFrom= (EditText)findViewById(R.id.book_fromDate);
        mDateTo= (EditText)findViewById(R.id.book_toDate);
        setdatefrom=findViewById(R.id.from_date);
        setdateTo=findViewById(R.id.to_date);
        setdatefrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate();
            }
        });
        setdateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate1();
            }
        });


        mFirebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1 =mname.getText().toString().trim();
                String phone1 =mPhone.getText().toString().trim();
                String type1 =mType.getText().toString().trim();
                datef1 =mDateFrom.getText().toString().trim();
                datet1 =mDateTo.getText().toString().trim();
                String email1=user.getEmail().toString().trim();
                Boolean result=false;
                Boolean review=false;


                FullInfoClass mInfo = new FullInfoClass(name1,email1,result,review,phone1,type1,datef1,datet1);


                mdatabase.child("BookInformation").push().setValue(mInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {

                            Toast.makeText(FullInformation.this,"Stored",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(FullInformation.this,HomePage.class);
                            startActivity(i);
                        }
                        else
                            Toast.makeText(FullInformation.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void getDate(){

        new DatePickerDialog(this,d, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show();
    }
    public void getDate1(){

        new DatePickerDialog(this,d1, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d= new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthofYear, int dayofMonth){

            cal.set(Calendar.YEAR,year);
            cal.set(Calendar.MONTH,monthofYear);
            cal.set(Calendar.DAY_OF_MONTH,dayofMonth);
            updateDate();

        }
    };
    DatePickerDialog.OnDateSetListener d1= new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthofYear, int dayofMonth){

            cal.set(Calendar.YEAR,year);
            cal.set(Calendar.MONTH,monthofYear);
            cal.set(Calendar.DAY_OF_MONTH,dayofMonth);
            updateDate1();

        }
    };


    public void updateDate(){


        mDateFrom.setText(formatDate.format(cal.getTime()));
        datef1 = mDateFrom.getText().toString();

    }

    public void updateDate1(){


        mDateTo.setText(formatDate.format(cal.getTime()));
        datet1 = mDateTo.getText().toString();

    }

}
