package com.example.omarelnajmi.deliverableonefirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RoleActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText textShortMessage;
    private Button buttonSignup;
    private String userId;
    private ProgressDialog progressDialog;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        textShortMessage = (EditText) findViewById(R.id.textShortMessage);


        buttonSignup.setOnClickListener(this);

    }

    private void signUpUser()
    {

        finish();
        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
    }

    @Override
    public void onClick(View v) {
        if (v == buttonSignup) {
//            String userRole = textShortMessage.getText().toString().trim();
//            progressDialog.setMessage("Registering as " + userRole);
//            progressDialog.show();
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

        }
    }
}
