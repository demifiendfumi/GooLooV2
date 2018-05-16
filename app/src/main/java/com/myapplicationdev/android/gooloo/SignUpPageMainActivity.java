package com.myapplicationdev.android.gooloo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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
                Intent j = new Intent(SignUpPageMainActivity.this, SignUpFaceBookActivity.class);
                startActivity(j);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String email = etEmail.getText().toString();
                String mobile = etMobile.getText().toString();
                final String password = etPassword.getText().toString();
                final String confirmPassword = etConfirmPassword.getText().toString();
                if(confirmPassword.equals(password)) {
                    RequestQueue queue = Volley.newRequestQueue(SignUpPageMainActivity.this);
                    String url ="http://10.0.2.2/gooloo/signup.php?firstName="+firstName+"&lastName="+lastName+"&email=" + email +"&mobile="+mobile+"&password=" + password;

                    // Request a json response from the provided URL.
                    JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                try {
                                    String msg = response.getString("");
                                    //Toast.makeText(SignUpPageMainActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("item", error.toString()+"");
                            if(error.toString().equals("com.android.volley.ParseError: org.json.JSONException: Value User registered successfully! of type java.lang.String cannot be converted to JSONObject")){
                                Toast toast = Toast.makeText(SignUpPageMainActivity.this, "Added Successfully", Toast.LENGTH_LONG);
                                toast.show();
                            }else if(error.toString().equals("com.android.volley.ParseError: org.json.JSONException: Value Email already exists, please try with another email. of type java.lang.String cannot be converted to JSONObject")){
                                Toast toast = Toast.makeText(SignUpPageMainActivity.this, "Email already exists, please try with another email", Toast.LENGTH_LONG);
                                toast.show();
                            }else{
                                Toast toast = Toast.makeText(SignUpPageMainActivity.this, "An unknown error has occurred. Please try again", Toast.LENGTH_LONG);
                                toast.show();
                            }

                        }
                    });

// Add the request to the RequestQueue.
                    queue.add(stringRequest);
                    String [] newUser = {firstName,lastName,email,mobile,password };
                    //test array
                    for(int x =0; x < newUser.length; x++){
                        Log.d("item", newUser[x]);
                    }
                    if(newUser[2].equals(email) && newUser[4].equals(password) && newUser[4].equals(confirmPassword)){

                    }else{
                        Toast toast = Toast.makeText(SignUpPageMainActivity.this, "email is taken/password does not match", Toast.LENGTH_LONG);
                        toast.show();
                    }

                    finish();
                }else{
                    Toast toast = Toast.makeText(SignUpPageMainActivity.this,"Password does not match",Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });

    }
}
