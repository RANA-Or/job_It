package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.logInPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment{

    private TextView fNameEditText , lastNameEditText , EmailEditText;
    private Button logOutBtn;
    private String fName, lName , eMail;
    private FirebaseAuth fAuth;
    private DocumentReference ref;
    private FirebaseFirestore fStore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        logOutBtn = rootView.findViewById(R.id.logOutBtn);
        fNameEditText = rootView.findViewById(R.id.firstNameTextView);
        lastNameEditText = rootView.findViewById(R.id.lastNameTextView);
        EmailEditText = rootView.findViewById(R.id.EmailTextView);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        return rootView;
    }


    public void onStart() {
        super.onStart();

        FirebaseUser user = fAuth.getCurrentUser();
        String Uid = user.getUid();
        ref = fStore.collection("Employers").document(Uid);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    fName = task.getResult().getString("fName");
                    lName = task.getResult().getString("lName");
                    eMail = task.getResult().getString("eMail");

                    fNameEditText.setText(fName);
                    lastNameEditText.setText(lName);
                    EmailEditText.setText(eMail);

                }
            }
        });


        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                Toast.makeText(getContext() , "Log out sucsses!" , Toast.LENGTH_SHORT).show();
                startActivity(new Intent(requireActivity() , logInPage.class));
            }
        });
    }


}