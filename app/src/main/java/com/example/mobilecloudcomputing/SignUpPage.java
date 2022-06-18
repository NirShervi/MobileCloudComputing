package com.example.mobilecloudcomputing;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button signUpButton;
    private EditText emailAddressET;
    private EditText passwordET;
    private String email="";
    private String password="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        signUpButton = findViewById(R.id.signup_button);
        emailAddressET = findViewById(R.id.signup_email);
        passwordET = findViewById(R.id.signup_password);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailAddressET.getText().toString();
                password = passwordET.getText().toString();
                Log.d("SignUp button clicked", "email and password has new values   email ="+ email+"    password = "+password);

                Log.d("Before register Acc", "values for registration   email ="+ email+"    password = "+password);
                createUser(mAuth,email,password);
            }

        });
        Log.d("Outside of if", "email and password has new values   email ="+ email+"    password = "+password);
//    if (email.isEmpty()== false  && password.isEmpty()== false) {
//        Log.d("Before register Acc", "values for registration   email ="+ email+"    password = "+password);
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(SignUpPage.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//                    }
//                });
//    }
    }

    private void createUser (FirebaseAuth mAuth,String email,String password){
        if (email.isEmpty()== false  && password.isEmpty()== false) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUpPage.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
    }

    private void updateUI(Object user) {
        if (user != null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(SignUpPage.this, "Error ! Log in failed",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }
}