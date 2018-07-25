package com.myapplicationdev.android.gooloo;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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


import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.Arrays;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.widget.ProfilePictureView;

public class MainActivity extends AppCompatActivity {
    EditText etEmail, etPass;
    Button btnLogin;
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

    private TextView info;
    private LoginButton loginButtonFB;
    private CallbackManager callbackManager;
    private Profile fbProfile;
    AccessTokenTracker accessTokenTracker;

    private ProfilePictureView profilePictureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = (EditText)findViewById(R.id. edtEmail);
        etPass = (EditText)findViewById(R.id. edtPassword);
        btnLogin = (Button)findViewById(R.id. btnLogin);
        tvSignUp = (TextView)findViewById(R.id. tvSignUp);
        tvForget = (TextView)findViewById(R.id. tvForget);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                password = etPass.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url ="http://ivriah.000webhostapp.com/gooloo/gooloo/login.php?email=" + email + "&password=" + password;

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

        //FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        loginButtonFB = (LoginButton)findViewById(R.id.login_button);
        loginButtonFB.setReadPermissions("public_profile","email");


        // Checking the Access Token.
        if(AccessToken.getCurrentAccessToken()!=null){

            GraphLoginRequest(AccessToken.getCurrentAccessToken());

            // If already login in then show the Toast.
            Toast.makeText(MainActivity.this,"Already logged in",Toast.LENGTH_SHORT).show();


        }else {

            // If not login in then show the Toast.
            Toast.makeText(MainActivity.this,"User not logged in",Toast.LENGTH_SHORT).show();
        }


        loginButtonFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Log.d("FB User ID: ",""+loginResult.getAccessToken().getUserId());
                Log.d( "FB Auth Token: ",""+loginResult.getAccessToken().getToken());
                fbProfile = Profile.getCurrentProfile();
                //profilePictureView.setProfileId(fbProfile.getId());
                GraphLoginRequest(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");
            }
        });
        // Detect user is login or not. If logout then clear the TextView and delete all the user info from TextView.
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
                if (accessToken2 == null) {

                }
            }
        };

        //get Key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.myapplicationdev.android.gooloo",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ForgetPassword.class);
                startActivity(intent);
            }
        });
    }

    // Method to access Facebook User Data.
    protected void GraphLoginRequest(final AccessToken accessToken){
        GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {

                        try {

                            // Adding all user info.
                            Log.d("FB Id: ",""+ jsonObject.getString("id"));
                            Log.d("FB FName: ",""+ jsonObject.getString("first_name"));
                            Log.d("FB LName: ",""+ jsonObject.getString("last_name"));
                            Log.d("FB Email: ",""+ jsonObject.getString("email"));

                            String userID = jsonObject.getString("id");
                            final String firstNameFB = jsonObject.getString("first_name");
                            final String lastNameFB = jsonObject.getString("last_name");
                            final String emailFB = jsonObject.getString("email");

                            Toast.makeText(MainActivity.this, ""+firstNameFB+" "+lastNameFB+" "+emailFB, Toast.LENGTH_SHORT).show();

                //Login
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String urlLogin = "http://ivriah.000webhostapp.com/gooloo/gooloo/loginFacebook.php?email=" + emailFB;
                // Request a json response from the provided URL.
                JsonObjectRequest stringRequestLogin = new JsonObjectRequest(Request.Method.GET, urlLogin, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    id = response.getString("id");
                                    Log.d("item", response.getString("id"));
                                    objEmail = response.getString("email");
                                    Log.d("item", response.getString("email"));
                                    lastName = response.getString("last_name");
                                    Log.d("item", response.getString("last_name"));
                                    firstName = response.getString("first_name");
                                    Log.d("item", response.getString("first_name"));
                                    String[] user = {id, objEmail, lastName, firstName};
                                    //test array
                                    for (int x = 0; x < user.length; x++) {
                                        Log.d("item", user[x]);
                                    }
                                    if (objEmail.equals(email)) {
                                        Intent j = new Intent(MainActivity.this, HomeActivity.class);
                                        j.putExtra("user", user);
                                        Log.d("user", user.length + "");
                                        startActivity(j);
                                    } else {
                                        Log.d("FB not in database", "email not in database");
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("item", error.toString() + "");

                    }
                });

                //Add the request to the RequestQueue.
                queue.add(stringRequestLogin);


                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
        Bundle bundle = new Bundle();
        bundle.putString(
                "fields",
                "id,name,email,gender,last_name,first_name"
        );
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(MainActivity.this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(MainActivity.this);

    }
    private boolean isLoggedIn() {
        return AccessToken.isCurrentAccessTokenActive()
                && !AccessToken.getCurrentAccessToken().getPermissions().isEmpty();
    }


}
