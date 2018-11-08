package com.example.omarelnajmi.deliverableonefirebase;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView userNameTextView;
    private TextView roleTextView;
    //   private TextView textViewUserEmail;

    private Button buttonLogout;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        } else {


            //      textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
            //    textViewUserEmail.setText("welcome " + user.getEmail());


            buttonLogout = (Button) findViewById(R.id.buttonLogout);
            buttonLogout.setOnClickListener(this);


            FirebaseDatabase.getInstance()
                    .getReference()
                    .child("User")
                    .child(user.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            UserInformation user = dataSnapshot.getValue(UserInformation.class); // this is your user

                            userNameTextView = (TextView) findViewById(R.id.userNameTextView);
                            roleTextView = (TextView) findViewById(R.id.roleTextView);

                            String userName = user.getUserName();
                            String userRole = user.getUserRole();

                            String setText = "Welcome " + userName + ". ";
                            String setTextTwo = "You are now logged on as a " + userRole + ". Add or Remove one of the following services: " +
                                    "Plumbing, Electrician, Landscaping, Snow Removal, Cleaning, Moving, Exterminating, Painting, Mould Remediation, Furniture Assembly.";

                            userNameTextView.setText(setText);
                            roleTextView.setText(setTextTwo);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }

    @Override
    public void onClick(View v)
    {
        if (v == buttonLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
