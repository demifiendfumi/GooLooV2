package com.myapplicationdev.android.gooloo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText etEmail, etPass;
    Button btnLogin, btnLoginFB;
    TextView tvSignUp,tvForget;
    String email = "";
    String password = "";
    String id = "";
    String objEmail="";
    String objPass ="";
    String lastName = "";
    String firstName = "";

    //ArrayList<String> user = new ArrayList<String>();
    //String [] user = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = (EditText)findViewById(R.id. edtEmail);
        etPass = (EditText)findViewById(R.id. edtPassword);
        btnLogin = (Button)findViewById(R.id. btnLogin);
        btnLoginFB = (Button)findViewById(R.id. btnLoginFacebook);
        tvSignUp = (TextView)findViewById(R.id. tvSignUp);
        tvForget = (TextView)findViewById(R.id. tvForget);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                password = etPass.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url ="http://10.0.2.2/gooloo/login.php?email=" + email + "&password=" + password;

                // Request a json response from the provided URL.
                JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    id = response.getString("id");
                                    Log.d("item", response.getString("id"));
                                    objEmail = response.getString("email");
                                    Log.d("item", response.getString("email"));
                                    objPass = response.getString("password");
                                    Log.d("item", response.getString("password"));
                                    lastName = response.getString("last_name");
                                    Log.d("item", response.getString("last_name"));
                                    firstName = response.getString("first_name");
                                    Log.d("item", response.getString("first_name"));
                                    String [] user = {id,objEmail, objPass, lastName, firstName};
                                    //test array
                                    for(int x =0; x < user.length; x++){
                                        Log.d("item", user[x]);
                                    }
                                    if(objEmail.equals(email) && objPass.equals(password)){
                                        Intent j = new Intent(MainActivity.this, HomeActivity.class);
                                        j.putExtra("user", user);
                                        Log.d("user", user.length+"");
                                        startActivity(j);
                                    }else{
                                        Toast toast = Toast.makeText(MainActivity.this, "email/password is incorrect", Toast.LENGTH_LONG);
                                        toast.show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("item", error.toString()+"");
                        Toast toast = Toast.makeText(MainActivity.this, "", Toast.LENGTH_LONG);
                        toast.show();
                    }

                });

// Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SignUpPageMainActivity.class);
                startActivity(i);
            }
        });
    }
}
