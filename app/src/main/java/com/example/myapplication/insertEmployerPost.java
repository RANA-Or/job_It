package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Model.Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class insertEmployerPost extends AppCompatActivity {


    private EditText job_title;
    private EditText job_desc;
    private EditText job_date;
    private EditText job_salary;
    private EditText job_location;
    private EditText job_email;
    private EditText job_contact_name;

    private Button button_post_job;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth fauth;
    DatabaseReference databaseReference;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_employer_post);


        // initializing our edittext and button
        job_title = findViewById(R.id.job_title);
        job_desc = findViewById(R.id.job_desc);
        job_date = findViewById(R.id.job_date);
        job_salary = findViewById(R.id.job_salary);
        job_email = findViewById(R.id.job_email1);
        job_location = findViewById(R.id.job_loc);
        job_contact_name = findViewById(R.id.job_content_memeber1);
        button_post_job = findViewById(R.id.job_post);


        //Present date by specific format
        SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");
        String now = ISO_8601_FORMAT.format(new Date());


        fauth = FirebaseAuth.getInstance();

        FirebaseUser fUser = fauth.getCurrentUser();
        assert fUser != null;
        String uID = fUser.getUid();
        // below line is used to get the
        // instance of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference().child("PostJobInfo").child(uID).child(now);

        // initializing our object
        // class variable.
        data = new Data();


        // adding on click listener for our button.
        button_post_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Present date by specific format
                SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");

                String timeNow = ISO_8601_FORMAT.format(new Date());
                // getting text from our edittext fields.
                String title = job_title.getText().toString();
                String description = job_desc.getText().toString();
                String dateJob = job_date.getText().toString();
                String salary = job_salary.getText().toString();
                String location = job_location.getText().toString();
                String contactName = job_contact_name.getText().toString();
                String email = job_email.getText().toString();
                String date = DateFormat.getDateInstance().format(new Date());



                // below line is for checking weather the
                // edittext fields are empty or not.
                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description) || TextUtils.isEmpty(dateJob) || TextUtils.isEmpty(salary)) {

                    // if the text fields are empty
                    // then show the below message.
                    Toast.makeText(insertEmployerPost.this, "Data is missing...", Toast.LENGTH_SHORT).show();
                } else {
                    // else call the method to add
                    // data to our database.
                    addDatatoFirebase(title,description,dateJob,salary,date,timeNow,location,contactName,email);
                    //Return to EmployerPage
                    openEmployerPage();
                }
            }
        });

    }

    private void addDatatoFirebase(String title, String description, String dateJob, String salary, String date, String timeNow, String location, String contactName, String email) {
        //Setting data in our object class
        data.setTitle(title);
        data.setDescription(description);
        data.setDateJob(dateJob);
        data.setSalary(salary);
        data.setDate(date);
        data.setTimeNow(timeNow);
        data.setLocation(location);
        data.setEmail(email);
        data.setContactName(contactName);


        //adding value event listener method which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.setValue(data);

                // after adding this data we are showing toast message.
                Toast.makeText(insertEmployerPost.this, "Data as been updated", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(insertEmployerPost.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void openEmployerPage(){
        Intent intent = new Intent(this , employerPage.class);
        startActivity(intent);
    }

}