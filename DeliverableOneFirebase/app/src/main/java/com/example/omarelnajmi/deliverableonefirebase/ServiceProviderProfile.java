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

public class ServiceProviderProfile extends AppCompatActivity implements View.OnClickListener {


    private Button buttonLogout;
    private Button buttonAddService;
    private Button buttonRemoveService;
    private Button buttonListOfServices;

    private EditText textTypeOfService;
    private EditText textAvailability;

    private ListView serviceList;
    private ArrayAdapter<String> myAdapter;
    private ArrayList<String> myList = new ArrayList<String>();

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        textTypeOfService = (EditText) findViewById(R.id.textTypeOfService);
        textAvailability = (EditText) findViewById(R.id.textAvailability);

        buttonAddService = (Button) findViewById(R.id.buttonAddService);
        buttonAddService.setOnClickListener(this);

        buttonRemoveService = (Button) findViewById(R.id.buttonRemoveService);
        buttonRemoveService.setOnClickListener(this);

        buttonListOfServices = (Button) findViewById(R.id.buttonListOfServices);
        buttonListOfServices.setOnClickListener(this);

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            buttonLogout = (Button) findViewById(R.id.buttonLogout);
            buttonLogout.setOnClickListener(this);


            serviceList = (ListView) findViewById(R.id.serviceList);
            final ArrayList<String> listOfServices = new ArrayList<String>();
            myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfServices);


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("Service");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot serviceSnap : dataSnapshot.getChildren()) {
                        Service service = serviceSnap.getValue(Service.class);

                        listOfServices.add(service.getServiceName());
                        myList = listOfServices;

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
        String serviceType;
        String availability;
        boolean add = false;
        if (v == buttonLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        if (v == buttonAddService) {

            serviceType = textTypeOfService.getText().toString().trim();
            availability = textAvailability.getText().toString().trim();

            if (TextUtils.isEmpty(availability)) {
                Toast.makeText(this, "Please enter availability", Toast.LENGTH_LONG).show();
                return;
            }


            if (TextUtils.isEmpty(serviceType)) {
                Toast.makeText(this, "Please enter a ServiceType", Toast.LENGTH_LONG).show();
                return;
            }

//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference ref = database.getReference("Service");
//            ref.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (DataSnapshot serviceListSnap : dataSnapshot.getChildren()) {
//                        ServiceProviderObject service = serviceListSnap.getValue(ServiceProviderObject.class);
//                        listOfServices.add(service.getServiceName());
//                    }
//                }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

            for (int i = 0; i < myList.size(); i++) {
                if (serviceType.equals(myList.get(i))) {
                    add = true;
                    break;
                }
            }
            if (add != true) {
                Toast.makeText(this, "Unavailable", Toast.LENGTH_LONG).show();
                return;
            } else if (add == true) {
                ServiceProviderObject newService = new ServiceProviderObject(serviceType, availability);

                FirebaseDatabase.getInstance().getReference("User").child(user.getUid()).child("Profile").child("Service").
                        child(newService.getServiceName()).
                        setValue(newService).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ServiceProviderProfile.this, "Service Added", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ServiceProviderProfile.this, "Could not add service", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
        if (v == buttonRemoveService){
            serviceType = textTypeOfService.getText().toString().trim();

            if (TextUtils.isEmpty(serviceType)) {
                Toast.makeText(this,"Please enter Service",Toast.LENGTH_LONG).show();
                return;
            }

            FirebaseDatabase.getInstance().getReference("User").child(user.getUid()).child("Profile").child("Service").
                    child(serviceType).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ServiceProviderProfile.this,"Service Removed",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        if (v == buttonListOfServices){
            Intent intent = new Intent(this, ServiceActivity.class);
            startActivity(intent);
        }
    }
}
