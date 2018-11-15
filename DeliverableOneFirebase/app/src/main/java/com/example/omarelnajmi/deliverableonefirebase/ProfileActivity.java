package com.example.omarelnajmi.deliverableonefirebase;

import android.content.ComponentName;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView userNameTextView;
    private TextView roleTextView;
    int counter = 0;


    //   private TextView textViewUserEmail;

    private Button buttonLogout;
    private Button buttonAddService;
    private Button buttonRemoveService;
    private Button buttonListOfServices;
    private Button buttonUpdateService;
    private FirebaseUser user;


    private EditText textTypeOfService;
    private EditText textHourlyWage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        buttonAddService = (Button) findViewById(R.id.buttonAddService);
        buttonRemoveService = (Button) findViewById(R.id.buttonRemoveService);
        buttonListOfServices = (Button) findViewById(R.id.buttonListOfServices);
        buttonUpdateService = (Button) findViewById(R.id.buttonUpdateService);

        buttonAddService.setOnClickListener(this);
        buttonRemoveService.setOnClickListener(this);
        buttonUpdateService.setOnClickListener(this);
        buttonListOfServices.setOnClickListener(this);

        textTypeOfService = (EditText) findViewById(R.id.textTypeOfService);
        textHourlyWage = (EditText) findViewById(R.id.textHourlyWage);


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

                            userNameTextView.setText(setText + setTextTwo);
//                            roleTextView.setText(setTextTwo);

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
        } else if (v == buttonAddService) {


            String serviceType = textTypeOfService.getText().toString().trim();

            if (TextUtils.isEmpty(serviceType)) {
                Toast.makeText(this,"Please enter Service",Toast.LENGTH_LONG).show();
                return;
            }



            String hourly = textHourlyWage.getText().toString().trim();

            if (TextUtils.isEmpty(hourly)) {
                Toast.makeText(this,"Please enter wage",Toast.LENGTH_LONG).show();
                return;
            }

            double hourlyWage = Double.parseDouble(hourly);
            Service newService = new Service(serviceType, hourlyWage);



            FirebaseDatabase.getInstance().getReference("Service").
                    child(newService.getServiceName()).
                    setValue(newService).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ProfileActivity.this,"Service Added",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ProfileActivity.this,"Could not add service",Toast.LENGTH_LONG).show();
                    }
                }
            });



        } else if (v == buttonRemoveService) {

            String serviceType = textTypeOfService.getText().toString().trim();

            FirebaseDatabase.getInstance().getReference("Service").
                    child(serviceType).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ProfileActivity.this,"Service Removed",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


        else if (v == buttonListOfServices) {
            Intent intent = new Intent(ProfileActivity.this, ServiceActivity.class);
            startActivity(intent);

        } else if (v == buttonUpdateService) {
            String serviceType1 = textTypeOfService.getText().toString().trim();

            FirebaseDatabase.getInstance().getReference("Service").
                    child(serviceType1).removeValue();

            String serviceType = textTypeOfService.getText().toString().trim();

            if (TextUtils.isEmpty(serviceType)) {
                Toast.makeText(this,"Please enter Service",Toast.LENGTH_LONG).show();
                return;
            }



            String hourly = textHourlyWage.getText().toString().trim();

            if (TextUtils.isEmpty(hourly)) {
                Toast.makeText(this,"Please enter wage",Toast.LENGTH_LONG).show();
                return;
            }

            double hourlyWage = Double.parseDouble(hourly);
            Service newService = new Service(serviceType, hourlyWage);



            FirebaseDatabase.getInstance().getReference("Service").
                    child(newService.getServiceName()).
                    setValue(newService).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ProfileActivity.this,"Service Added",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ProfileActivity.this,"Could not add service",Toast.LENGTH_LONG).show();
                    }
                }
            });


        }
    }
}
