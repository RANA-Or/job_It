package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.Model.Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class detailedJobData extends AppCompatActivity {

    private FirebaseAuth fauth;
    private DatabaseReference ref;
    private Button goToChat;
    EditText email1;
    ArrayList<Data> dataList;
    RecyclerView recyclerView;
    adapterClass adapterClass;
    ArrayList<Data> arrayListCopy;

    private TextView dTitle;
    private TextView dDate;
    private TextView dLocation;
    private TextView dSalary;
    private TextView dEmail;
    private TextView dContentName;
    private TextView dJobDate;
    private TextView dDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_job_data);

        goToChat = findViewById(R.id.button_enter_chat);
        fauth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        email1 = findViewById(R.id.emailEditText);


        dTitle = (TextView) findViewById(R.id.job_detail_title);
        dDate = (TextView) findViewById(R.id.job_details_date_board);
        dDescription = (TextView) findViewById(R.id.job_details_desc);
        dJobDate = (TextView) findViewById(R.id.job_details_job_date);
        dSalary = (TextView) findViewById(R.id.job_details_salary);
        dEmail = (TextView) findViewById(R.id.job_details_email);
        dContentName = (TextView) findViewById(R.id.job_details_contact_name);
        dLocation = (TextView) findViewById(R.id.job_details_location);


        //Receive data from all job activity using intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String date = intent.getStringExtra("date");
        String description = intent.getStringExtra("description");
        String jobDate = intent.getStringExtra("jobDate");
        String salary = intent.getStringExtra("salary");
        String email = intent.getStringExtra("email");
        String contactName = intent.getStringExtra("contactName");
        String location = intent.getStringExtra("location");

        //Setting values
        dTitle.setText(title);
        dDate.setText(date);
        dDescription.setText(description);
        dJobDate.setText(jobDate);
        dSalary.setText(salary);
        dEmail.setText(email);
        dContentName.setText(contactName);
        dLocation.setText(location);

        goToChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUser();
            }
        });


    }

    public void sendUser(){
        Intent intent = new Intent(detailedJobData.this , ChatListActivity.class);
        startActivity(intent);
    }
}