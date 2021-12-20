package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.Data;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private TextView dSkills;
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
        dSkills = (TextView) findViewById(R.id.job_details_skills);
        dSalary = (TextView) findViewById(R.id.job_details_salary);
        dEmail = (TextView) findViewById(R.id.job_details_email);
        dContentName = (TextView) findViewById(R.id.job_details_contact_name);
        dLocation = (TextView) findViewById(R.id.job_details_location);


        //Receive data from all job activity using intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String date = intent.getStringExtra("date");
        String description = intent.getStringExtra("description");
        String skills = intent.getStringExtra("skills");
        String salary = intent.getStringExtra("salary");
        String email = intent.getStringExtra("email");
        String contactName = intent.getStringExtra("contactName");
        String location = intent.getStringExtra("location");

        //Setting values
        dTitle.setText(title);
        dDate.setText(date);
        dDescription.setText(description);
        dSkills.setText(skills);
        dSalary.setText(salary);
        dEmail.setText(email);
        dContentName.setText(contactName);
        dLocation.setText(location);

    }
}