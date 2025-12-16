package com.example.login_page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    EditText email, username, password, dob;
    Button signup;
    TextView login;
    DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.Email);
        username = findViewById(R.id.Username);
        password = findViewById(R.id.Password);
        dob = findViewById(R.id.Dob);

        signup = findViewById(R.id.btnSignup);
        login = findViewById(R.id.tvLogin);

        db = new DataBaseHandler(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String e = email.getText().toString();
                String u = username.getText().toString();
                String p = password.getText().toString();
                String d = dob.getText().toString();

                if (e.isEmpty()) {
                    email.setError("Email required");
                    return;
                }
                if (u.isEmpty()) {
                    username.setError("Username required");
                    return;
                }
                if (p.isEmpty()) {
                    password.setError("Password required");
                    return;
                }
                if (d.isEmpty()) {
                    dob.setError("Date of birth required");
                    return;
                }

                long id = db.addUser(e, u, p, d);

                if (id > 0) {
                    Toast.makeText(MainActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }
}
