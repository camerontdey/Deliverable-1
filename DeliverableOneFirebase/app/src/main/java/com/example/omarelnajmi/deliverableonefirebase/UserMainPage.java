package com.example.omarelnajmi.deliverableonefirebase;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;

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

public class UserMainPage extends AppCompatActivity implements View.OnClickListener {

        private Button button_Search;
        private Button button_logout;

        private EditText ServiceSearch;
        private EditText TimeSearch;
        private EditText RateSearch;

        private TextView userNameTextView;
        private TextView roleTextView;

        private FirebaseAuth firebaseAuth;
        private FirebaseUser user;

        private String availibility;
        private String serviceName;
        private String rate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

       ServiceSearch = (EditText) findViewById(R.id.ServiceSearch);
       TimeSearch = (EditText) findViewById(R.id.TimeSearch);
       RateSearch = (EditText) findViewById (R.id.RateSearch);


        button_Search = (Button) findViewById(R.id.button_Search);
        button_Search.setOnClickListener(this);


        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        else{
            button_logout = (Button) findViewById(R.id.button_logout);
            button_logout.setOnClickListener(this);

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
                            String setTextTwo = "You are now logged on as a " + userRole + ".";

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
    public void onClick(View v) {
        String rating;
        String service;
        String availability;
        if (v == button_logout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if(v == button_Search){
            service = ServiceSearch.getText().toString().trim();
            rating = RateSearch.getText().toString().trim();
            availability = TimeSearch.getText().toString().trim();

            if (TextUtils.isEmpty(availability)&&TextUtils.isEmpty(service)&&TextUtils.isEmpty(rating)) {
                Toast.makeText(this, "Please fill in one of the above fields", Toast.LENGTH_LONG).show();
                return;
            }

            if(TextUtils.isEmpty(rating)){
                rating= null;
            }

            if(TextUtils.isEmpty(service)){
                service= null;
            }

            if(TextUtils.isEmpty(availability)){
                availability = null;
            }

            UserObject userObject = new UserObject(service,availability,rating);

            startActivity(new Intent(this, UserSearch.class));
        }
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public String getAvailability(){
        return this.availibility;
    }

    public String getRate(){
        return this.rate;
    }

    public void setServiceName(String serviceName){
        this.serviceName = serviceName;
    }

    public void setAvailability(String availability){
        this.availibility = availability;
    }

    public void setRate(String rate){
        this.rate = rate;

    }




}

