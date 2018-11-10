package com.example.omarelnajmi.deliverableonefirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.ArrayList;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {


    private Button buttonBack;
    private ListView serviceList;
    private ArrayAdapter<String> myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        serviceList = (ListView)findViewById(R.id.serviceList);
        final ArrayList<String> listOfServices = new ArrayList<String>();
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfServices);
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(this);


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("Service");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot serviceSnap:dataSnapshot.getChildren()){
                            Service service =serviceSnap.getValue(Service.class);

                            listOfServices.add(service.getServiceName());

                        }
                        serviceList.setAdapter(myAdapter);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



    }

    @Override
    public void onClick(View v) {
        if (v == buttonBack) {
            Intent intent = new Intent(ServiceActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
    }
}
