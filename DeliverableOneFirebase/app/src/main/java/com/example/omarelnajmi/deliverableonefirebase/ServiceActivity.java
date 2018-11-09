package com.example.omarelnajmi.deliverableonefirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {


    private Button buttonBack;

    private TextView serviceTypeTextView;
    private TextView serviceHourlyWage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);




        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(this);


        FirebaseDatabase.getInstance().getReference().child("Service").
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<String> names= new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Service service = snapshot.child("Service").child("serviceName").getValue(Service.class);
                            serviceTypeTextView = (TextView) findViewById(R.id.serviceTypeTextView);
                            String serviceType = service.getServiceName();
                            names.add(serviceType);
                            String setText = serviceType;
                            serviceTypeTextView.setText(setText);

                        }
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
