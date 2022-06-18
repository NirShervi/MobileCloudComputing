package com.example.mobilecloudcomputing;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
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

public class MainActivity extends AppCompatActivity {
    TextView signUplink;
    private FirebaseAuth mAuth;
    private EditText emailAddressET;
    private EditText passwordET;
    private Button signInButton;
    private String email="";
    private String password="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        signUplink = (TextView) findViewById(R.id.registerButton);
        emailAddressET = findViewById(R.id.signin_email);
        passwordET = findViewById(R.id.signin_password);
        signInButton = findViewById(R.id.signInButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailAddressET.getText().toString();
                password = passwordET.getText().toString();
                Log.d("SignIn button clicked", "email and password has new values   email ="+ email+"    password = "+password);
                logInUser(mAuth,email,password);
            }
        });
        signUplink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUpPage.class);
                startActivity(intent);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private void logInUser(FirebaseAuth mAuth, String email, String password){
        if (email.isEmpty()== false  && password.isEmpty()== false) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
    }

    private void updateUI(Object user) {
        if (user != null){
            Intent intent = new Intent(getApplicationContext(),PostgresActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(MainActivity.this, "Error ! Registration failed",
                    Toast.LENGTH_SHORT).show();
        }
    }
}