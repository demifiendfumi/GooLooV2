package com.myapplicationdev.android.gooloo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPassword extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        final EditText etEmailForget;
        Button btnSend;

        etEmailForget =(EditText)findViewById(R.id.etEmailForgetPassword);
        btnSend = (Button)findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmailForget.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(ForgetPassword.this);
                String url ="http://ivriah.000webhostapp.com/gooloo/gooloo/doResetPassword.php?email=" + email;
                //http://10.0.2.2/gooloo/
                //http://ivriah.000webhostapp.com/gooloo/gooloo/

                // Request a json response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @TargetApi(Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    String msg = jsonArray.getString(0);
                                    Log.d("Reponse", response.toString());

                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Forget Password", error.toString()+"");
                        Toast toast = Toast.makeText(ForgetPassword.this, ""+error.toString(), Toast.LENGTH_LONG);
                        toast.show();
                    }
                });

// Add the request to the RequestQueue.
                queue.add(stringRequest);

            }
        });
    }
}
