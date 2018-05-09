package com.myapplicationdev.android.gooloo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpPageMainActivity extends AppCompatActivity {

    TextView tvSignupFB, tvFirstName, tvLastName, tvEmail, tvMobile, tvPass,tvCPass;
    EditText etFirstName, etLastName, etEmail, etMobile, etPassword, etConfirmPassword;
    Button btnConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        tvEmail = (TextView)findViewById(R.id.tvEmail);
        tvSignupFB = (TextView)findViewById(R.id.tvSignupFB);

        etFirstName = (EditText)findViewById(R.id.etFirstname);
        etLastName = (EditText)findViewById(R.id.etLastName);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etMobile = (EditText)findViewById(R.id.etMobileNo);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etConfirmPassword = (EditText)findViewById(R.id.etConfirmPassword);

        btnConfirm = (Button)findViewById(R.id.btnConfirm);

        tvSignupFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String email = etEmail.getText().toString();
                String mobile = etMobile.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();

                //Toast.makeText(SignUpPageMainActivity.this, ""+firstName+lastName+email+mobile+password+confirmPassword, Toast.LENGTH_SHORT).show();
                finish();

            }
        });

    }
}
