package com.example.omarelnajmi.deliverableonefirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PersonalProviderProfile extends AppCompatActivity implements View.OnClickListener {

    private Button buttonBack;
    private ListView serviceList;
    private ArrayAdapter<String> myAdapter;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_provider_profile);

        serviceList = (ListView)findViewById(R.id.serviceList);
        final ArrayList<String> listOfServices = new ArrayList<String>();
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfServices);
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();



        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));

        } else {

            FirebaseDatabase.getInstance()
                    .getReference("User")
                    .child(user.getUid())
                    .child("Profile")
                    .child("Service")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot serviceSnap : dataSnapshot.getChildren()) {
                                ServiceProviderObject availabilities = serviceSnap.getValue(ServiceProviderObject.class);
                                String toString = availabilities.toString();
                                listOfServices.add(toString);
                            }
                            serviceList.setAdapter(myAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }


    }

    @Override
    public void onClick(View v) {
        if (v == buttonBack) {
            Intent intent = new Intent(PersonalProviderProfile.this, ServiceProviderProfile.class);
            startActivity(intent);
        }
    }
}
