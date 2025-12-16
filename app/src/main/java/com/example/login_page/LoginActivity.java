package com.example.login_page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button login , Signup;
    TextView forgot, delete;
    DataBaseHandler db;

    boolean isLoggedIn = false;
    String loggedUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        login = findViewById(R.id.btnLogin);
        forgot = findViewById(R.id.tvForgot);
        delete = findViewById(R.id.tvDelete);
        Signup = findViewById(R.id.btnGoSignup);
        db = new DataBaseHandler(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String u = username.getText().toString();
                String p = password.getText().toString();
                if (u.isEmpty()) {
                    username.setError("Username required");
                    return;
                }

                if (p.isEmpty()) {
                    password.setError("Password required");
                    return;
                }

                if (db.checkLogin(u, p)) {
                    isLoggedIn = true;
                    loggedUser = u;
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                } else {
                    isLoggedIn = false;
                    Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isLoggedIn) {
                    boolean deleted = db.delete_User(loggedUser);

                    if (deleted) {
                        Toast.makeText(LoginActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                        isLoggedIn = false;
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Delete Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Please login first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        forgot.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, ResetActivity.class))
        );



        Signup.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        });

    }
}
