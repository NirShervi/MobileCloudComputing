package com.example.mobilecloudcomputing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MySQLActivity extends AppCompatActivity {
    private String username = "";
    private String password = "";
    private EditText emailAddress_mysql;
    private EditText password_mysql;
    private Button signInButton_mysql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sqlactivity);
        emailAddress_mysql = findViewById(R.id.signin_email_mysql);
        password_mysql = findViewById(R.id.signin_password_mysql);
        signInButton_mysql = findViewById(R.id.signInButton_mysql);

        signInButton_mysql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = emailAddress_mysql.getText().toString();
                password = password_mysql.getText().toString();
                Log.d("SignIn button clicked", "email and password has new values   email ="+ username +"    password = "+password);
                MySQLReadDB db = new MySQLReadDB(username,password);
                try {
                    if(db.verfUserSQL()=="True"){
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

