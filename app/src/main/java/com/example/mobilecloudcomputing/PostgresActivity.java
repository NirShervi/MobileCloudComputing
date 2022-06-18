package com.example.mobilecloudcomputing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PostgresActivity extends AppCompatActivity {
    private String username = "";
    private String password = "";
    private EditText emailAddress_pg;
    private EditText password_pg;
    private Button signInButton_pg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postgres);
        emailAddress_pg = findViewById(R.id.signin_email_postgres);
        password_pg = findViewById(R.id.signin_password_postgres);
        signInButton_pg = findViewById(R.id.signInButton_postgres);



        signInButton_pg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = emailAddress_pg.getText().toString();
                password = password_pg.getText().toString();
                Log.d("SignIn button clicked", "email and password has new values   email ="+ username +"    password = "+password);
                PostgresReadDB db = new PostgresReadDB(username,password);
                try {
                    if(db.verfUser()=="True"){
                        Intent intent = new Intent(getApplicationContext(),MySQLActivity.class);
                        startActivity(intent);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }






}