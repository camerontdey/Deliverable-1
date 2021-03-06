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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    private TextView userNameTextView;
    private TextView roleTextView;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignin);
        textViewSignup  = (TextView) findViewById(R.id.textViewSignUp);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }



        buttonSignIn.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
    }





    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Logging In Please Wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            finish();



                            firebaseAuth = FirebaseAuth.getInstance();
                            user = firebaseAuth.getCurrentUser();



                                //      textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
                                //    textViewUserEmail.setText("welcome " + user.getEmail());



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

                                                //String userName = user.getUserName();
                                                String userRole = user.getUserRole();

                                                if (userRole.equals("admin")) {
                                                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                                                } else if (userRole.equals("Service Provider")){

                                                    startActivity(new Intent(getApplicationContext(), ServiceProviderActivity.class));
                                                } else if(userRole.equals("Home Owner")){
                                                    startActivity(new Intent(getApplicationContext(), UserMainPage.class));
                                                }

                                                else {
                                                    startActivity(new Intent(getApplicationContext(), UserMainPage.class));
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });


                        } else {
                            Toast.makeText(LoginActivity.this,"Invalid Email or Password",Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }





    @Override
    public void onClick(View v) {
        if (v == buttonSignIn) {
            userLogin();

        }

        if (v == textViewSignup) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

    }


}
