package com.myapplicationdev.android.gooloo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpFacebook extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupfacebook);
        RequestQueue queue = Volley.newRequestQueue(SignUpFacebook.this);

        String firstName = getIntent().getExtras().getString("name");
        String lastName = getIntent().getExtras().getString("last_name");
        String email = getIntent().getExtras().getString("email");

        String url = "http://ivriah.000webhostapp.com/gooloo/gooloo/signup.php?firstName=" + firstName + "&lastName=" + lastName + "&email=" + email + "&mobile=" + "" + "&password=" + "";

        // Request a json response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String msg = response.getString("");
                            Log.d("SignUpFacebook","Added successfully");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("item", error.toString() + "");
                if (error.toString().equals("com.android.volley.ParseError: org.json.JSONException: Value User registered successfully! of type java.lang.String cannot be converted to JSONObject")) {
                    Toast toast = Toast.makeText(SignUpFacebook.this, "Added Successfully", Toast.LENGTH_LONG);
                    toast.show();
                } else if (error.toString().equals("com.android.volley.ParseError: org.json.JSONException: Value Email already exists, please try with another email. of type java.lang.String cannot be converted to JSONObject")) {
                    Toast toast = Toast.makeText(SignUpFacebook.this, "Email already exists, please try with another email", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(SignUpFacebook.this, "An unknown error has occurred. Please try again", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
        String[] newUser = {firstName, lastName, email};
        //test array
        for (int x = 0; x < newUser.length; x++) {
            Log.d("item", newUser[x]);
        }

}
}
