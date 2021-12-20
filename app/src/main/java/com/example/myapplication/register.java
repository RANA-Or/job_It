package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class register extends AppCompatActivity {

    private Button employee , employer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        employee = (Button) findViewById(R.id.employeeButton);
        employer = (Button) findViewById(R.id.emplyerButton);

        employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmployeeRegisterPage();
            }
        });

        employer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmployerRegisterPage();
            }
        });
    }

    public void openEmployeeRegisterPage(){
        Intent intent = new Intent(this , employeeRegister.class);
        startActivity(intent);
    }

    public void openEmployerRegisterPage(){
        Intent intent = new Intent(this , employerRegister.class);
        startActivity(intent);
    }
}