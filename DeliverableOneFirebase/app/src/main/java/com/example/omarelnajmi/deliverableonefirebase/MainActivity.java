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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText textShortMessage;
    private EditText editTextName;

    private TextView textViewSignin;
    private Button buttonRegister;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.buttonSignup);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textShortMessage = (EditText) findViewById(R.id.textShortMessage);
        editTextName = (EditText) findViewById(R.id.editTextName);

        textViewSignin = (TextView) findViewById(R.id.textViewSignin);


        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }



    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();
        final String userRole = textShortMessage.getText().toString().trim();
        final String userName = editTextName.getText().toString().trim();



        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(userRole)) {
            Toast.makeText(this,"Please enter user role",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            UserInformation userInformation = new UserInformation(userRole, userName);
                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(userInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                                    } else {
                                        Toast.makeText(MainActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                                    }

                                    progressDialog.dismiss();

                                }
                            });

//                            finish();
//                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

                        } else {

                            finish();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));

//                            Toast.makeText(MainActivity.this,"Registration Error",Toast.LENGTH_LONG).show();

                        }

                    }
                });
    }


    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            registerUser();
        }

        if (v == textViewSignin) {

            startActivity(new Intent(this, LoginActivity.class));

        }

    }
}
