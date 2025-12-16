package com.example.login_page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResetActivity extends AppCompatActivity {

    EditText email, newPassword;
    Button reset, back;
    DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        email = findViewById(R.id.etEmail);
        newPassword = findViewById(R.id.etNewPassword);
        reset = findViewById(R.id.btnReset);
        back = findViewById(R.id.btnBack);

        db = new DataBaseHandler(this);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String e = email.getText().toString();
                String p = newPassword.getText().toString();

                if (db.checkUserByEmail(e)) {
                    boolean updated = db.updatePasswordByEmail(e, p);
                    if (updated) {
                        Toast.makeText(ResetActivity.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ResetActivity.this, "Password Update Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ResetActivity.this, "Email not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResetActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
