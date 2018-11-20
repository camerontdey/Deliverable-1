package com.example.omarelnajmi.deliverableonefirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServiceProviderActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView userNameTextView;
    private TextView roleTextView;

    private EditText editTextAddress;
    private EditText editNameOfCompany;
    private EditText editPastExperience;
    private EditText editLicensed;

    private ProgressDialog progressDialog;

    private Button buttonLogout;
    private Button buttonCompleteProfile;
    private Button buttonContinue;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        progressDialog = new ProgressDialog(this);

        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editNameOfCompany = (EditText) findViewById(R.id.editNameOfCompany);
        editPastExperience = (EditText) findViewById(R.id.editPastExperience);
        editLicensed = (EditText) findViewById(R.id.editLicensed);

        buttonCompleteProfile = (Button) findViewById(R.id.buttonCompleteProfile);
        buttonCompleteProfile.setOnClickListener(this);
        buttonContinue = (Button) findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener(this);


        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        } else {

            buttonLogout = (Button) findViewById(R.id.buttonLogout);
            buttonLogout.setOnClickListener(this);
        }


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

                        String setText = "Welcome " + userName + ". You are now logged on as a " + userRole;
//                        String setTextTwo = "You are now logged on as a " + userRole + ".";

                        userNameTextView.setText(setText);
//                        roleTextView.setText(setTextTwo);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    @Override
    public void onClick(View v) {
        if ( v == buttonLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        if (v == buttonCompleteProfile) {

            String userAddress = editTextAddress.getText().toString().trim();
            if (TextUtils.isEmpty(userAddress)) {
                Toast.makeText(this,"Please enter address",Toast.LENGTH_LONG).show();
                return;
            }


            String userCompany = editNameOfCompany.getText().toString().trim();
            if (TextUtils.isEmpty(userCompany)) {
                Toast.makeText(this,"Please enter name of company",Toast.LENGTH_LONG).show();
                return;
            }


            String userExperience = editPastExperience.getText().toString().trim();
            if (TextUtils.isEmpty(userExperience)) {
                Toast.makeText(this,"Please enter experience",Toast.LENGTH_LONG).show();
                return;
            }


            String userLicense = editLicensed.getText().toString().trim();
            if (TextUtils.isEmpty(userLicense)) {
                Toast.makeText(this,"Please enter whether you are licensed or not",Toast.LENGTH_LONG).show();
                return;
            }


            ServiceProvider newProfile = new ServiceProvider(userAddress, userCompany, userExperience, userLicense);


            progressDialog.setMessage("Completing Profile...");
            progressDialog.show();

            FirebaseDatabase.getInstance().getReference("User").child(user.getUid())
                    .child("Profile").setValue(newProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ServiceProviderActivity.this,"Profile Completed",Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), ServiceProviderProfile.class));
                    } else {
                        Toast.makeText(ServiceProviderActivity.this,"Unable to Complete Profile",Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }
            });

        }

        if (v == buttonContinue) {
            finish();
            startActivity(new Intent(getApplicationContext(), ServiceProviderProfile.class));
        }
    }
}
