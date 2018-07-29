package com.myapplicationdev.android.gooloo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewProfile extends AppCompatActivity {
    ImageView ivProfile;
    TextView firstName, lastName, email, mobile, address, company;
    Button btnBack, btnEdit;
    String user[];
    String user_detail[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        ivProfile = findViewById(R.id. ivProfile);
        firstName = findViewById(R.id.textViewFirstName);
        lastName = findViewById(R.id. textViewLastName);
        email = findViewById(R.id. textViewEmail);
        mobile = findViewById(R.id. textViewMobile);
        address = findViewById(R.id. textViewAddress);
        company = findViewById(R.id. textViewCompany);
        btnBack = findViewById(R.id. btnBack);
        btnEdit = findViewById(R.id. btnEdit);
    }

    public void onResume(){
        super.onResume();
        Intent i = getIntent();
        user = i.getStringArrayExtra("user");
        user_detail = i.getStringArrayExtra("user_detail");

        if(user_detail!= null && user_detail.length>0){
            mobile.setText(user_detail[2]);
            address.setText(user_detail[0]);
            company.setText(user_detail[1]);
        }else{
            HttpRequest request = new HttpRequest
                    ("http://ivriah.000webhostapp.com/gooloo/gooloo/viewProfile.php?firstName="+ user[4] +"&lastName=" + user[3]);
            request.setOnHttpResponseListener(mHttpResponseListener);
            request.setMethod("GET");
            request.execute();
        }

        firstName.setText(user[4]);
        lastName.setText(user[3]);
        email.setText(user[1]);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewProfile.this, EditProfile.class);
                i.putExtra("user", user);
                i.putExtra("user_detail", user_detail);
                startActivity(i);

            }
        });
    }

    private HttpRequest.OnHttpResponseListener mHttpResponseListener =
            new HttpRequest.OnHttpResponseListener() {
                @Override
                public void onResponse(String response){
                    // process response here
                    Log.i("JSON Results: ", response);
                    try {
                        JSONArray jsonArr = new JSONArray(response);
                        JSONObject jsonObj = jsonArr.getJSONObject(0);
                        address.setText(jsonObj.getString("address"));
                        company.setText(jsonObj.getString("company_name"));
                        mobile.setText(jsonObj.getString("mobile"));
                        user_detail = new String[]{jsonObj.getString("address"), jsonObj.getString("company_name"), jsonObj.getString("mobile")};

                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }

                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.homeSelection) {
            Intent i = new Intent(this, HomeActivity.class);
            i.putExtra("user", user);
            if(user_detail!= null && user_detail.length>0){
                i.putExtra("user_detail", user_detail);
            }
            startActivity(i);
            return true;
        }else if (id == R.id.profileSelection) {
//            Intent i = new Intent(getBaseContext(), HomeActivity.class);
//            startActivity(i);
            Log.d("profile", "Profile Selected");
            return true;
        }else if (id == R.id.cartSelection) {
            Toast.makeText(this, "You are at the Cart Page", Toast.LENGTH_SHORT).show();
            return true;
        }else if (id == R.id.orderSelection) {
            Intent i = new Intent(this, OrderPage.class);
            i.putExtra("user", user);
            startActivity(i);
            Log.d("view order", "Order selected");
            return true;
        }else if(id == R.id.logout){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
