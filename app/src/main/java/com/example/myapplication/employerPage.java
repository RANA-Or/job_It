package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.Fragment.HomeFragment;
import com.example.myapplication.Fragment.PostFragment;
import com.example.myapplication.Fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class employerPage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    private Button chatButt;
    String otherEmail;
    static List<String> emails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_page);

        chatButt = findViewById(R.id.chatB);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        chatButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                updateFrindUsers();
                openUserChat();
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container , new HomeFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment=null;
                switch (item.getItemId()){
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        break;

                    case R.id.nav_post:
                        fragment = new PostFragment();
                        break;

                    case  R.id.nav_profile:
                        fragment = new ProfileFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.main_container , fragment).commit();

                return true;
            }
        });



//        btnAllJob = findViewById(R.id.btn_empAllJobs);
//        btnPostJob = findViewById(R.id.btn_postJob);
//        btnAllJob.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                startActivity(new Intent(getApplicationContext(), allJobs.class));
//            }
//        });
//
//        btnPostJob.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                startActivity(new Intent(getApplicationContext(), postJob.class));
//
//            }
//        });

    }
    public void openUserChat() {
        Intent intent = new Intent(this, ChatListActivity.class);
        startActivity(intent);
    }

    public void updateFrindUsers(){

        emails = new LinkedList<String>();

        String email = mAuth.getCurrentUser().getEmail();

        databaseReference.child("chats").child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        if(dataSnapshot.child("sender").getValue().toString().equals(email)){
                            otherEmail = dataSnapshot.child("receiver").getValue().toString();
                            ChatListActivity.flag = true;
                        }
                        if(dataSnapshot.child("receiver").getValue().toString().equals(email)){
                            otherEmail = dataSnapshot.child("sender").getValue().toString();
                            ChatListActivity.flag = true;
                        }
                        emails.add(otherEmail);
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}