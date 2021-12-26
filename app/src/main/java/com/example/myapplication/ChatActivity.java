package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ChatActivity extends AppCompatActivity {

    EditText message;
    Button send;
    ListView chatListView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> messages = new ArrayList<>();
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    DatabaseReference ref;
    Map<FirebaseUser, Map<String , Object> > messagesData;
    static int count ;
    static int counter = 1;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        message = findViewById(R.id.message);
        send = findViewById(R.id.send);
        chatListView = findViewById(R.id.chatListView);

        Intent intent = getIntent();

        String otherEmail = intent.getStringExtra("email");
        String email = mAuth.getCurrentUser().getEmail();

        setTitle("Chat with : " + otherEmail);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(message.getText().toString().isEmpty()){
                    Toast.makeText(ChatActivity.this , "Write a Message!" , Toast.LENGTH_SHORT).show();
                }else{
                    databaseReference.child("chats").child("users").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    if((dataSnapshot.child("sender").getValue().toString().equals(email) &&
                                            dataSnapshot.child("receiver").getValue().toString().equals(otherEmail))||(
                                            (dataSnapshot.child("sender").getValue().toString().equals(otherEmail) &&
                                             dataSnapshot.child("receiver").getValue().toString().equals(email)))){
                                        count = Integer.parseInt(Objects.requireNonNull(dataSnapshot.getKey()));
                                        flag = true;
                                    }
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    Map<String , Object> messageData = new HashMap<>();
                    messageData.put("sender" , email);
                    messageData.put("receiver" ,otherEmail);
                    messageData.put("message" ,message.getText().toString());

                    databaseReference.child("messages").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!flag){
                                //count = (int) (snapshot.getChildrenCount() + 1);
                                //count++;
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        counter++;
                                        count = (int) (snapshot.getChildrenCount() + counter);
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                            databaseReference.child("chats").child("users").child(String.valueOf(count)).setValue(messageData).
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                message.setText("");

                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ChatActivity.this , "Error in sending message" + e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        databaseReference.child("chats").child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        if((dataSnapshot.child("sender").getValue().toString().equals(email) &&
                            dataSnapshot.child("receiver").getValue().toString().equals(otherEmail))||(
                                (dataSnapshot.child("sender").getValue().toString().equals(otherEmail) &&
                                 dataSnapshot.child("receiver").getValue().toString().equals(email)))){
                            String message = dataSnapshot.child("message").getValue().toString();
                            if(!dataSnapshot.child("sender").getValue().toString().equals(email)){
                                message = ">" + message;
                            }
                            messages.add(message);
                        }
                    }
                    arrayAdapter = new ArrayAdapter(ChatActivity.this , android.R.layout.simple_list_item_1,messages);
                    chatListView.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}