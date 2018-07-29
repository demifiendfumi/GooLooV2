package com.myapplicationdev.android.gooloo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class EditProfile extends AppCompatActivity {
    ImageView ivEditProfile;
    EditText etfirstName, etlastName, etemail, etmobile, etaddress, etcompany;
    Button btnCancel, btnSave;
    String user[];
    String user_detail[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ivEditProfile = findViewById(R.id. ivEditProfile);
        etfirstName = findViewById(R.id.editTextFirstName);
        etlastName = findViewById(R.id. editTextLastName);
        etemail = findViewById(R.id. editTextEmail);
        etmobile = findViewById(R.id. editTextMobile);
        etaddress = findViewById(R.id. editTextAddress);
        etcompany = findViewById(R.id. editTextCompany);
        btnCancel = findViewById(R.id. btnCancel);
        btnSave = findViewById(R.id. btnSave);

        Intent i = getIntent();
        user = i.getStringArrayExtra("user");
        user_detail = i.getStringArrayExtra("user_detail");

        etfirstName.setText(user[4]);
        etlastName.setText(user[3]);
        etemail.setText(user[1]);
        etemail.setFocusable(false);
        etmobile.setText(user_detail[2]);
        etaddress.setText(user_detail[0]);
        etcompany.setText(user_detail[1]);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpRequest request = new HttpRequest
                        ("http://ivriah.000webhostapp.com/gooloo/gooloo/updateProfile.php");
                //http://10.0.2.2/gooloo/
                //http://ivriah.000webhostapp.com/gooloo/gooloo/
                request.setOnHttpResponseListener(mHttpResponseListener);
                request.setMethod("POST");
                request.addData("id", user[0]);
                request.addData("firstName", etfirstName.getText().toString());
                request.addData("lastName", etlastName.getText().toString());
                request.addData("email", etemail.getText().toString());
                request.addData("mobile", etmobile.getText().toString());
                request.addData("address", etaddress.getText().toString());
                request.addData("company", etcompany.getText().toString());
                request.execute();
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
                        JSONObject jsonObj = new JSONObject(response);
                        Toast.makeText(EditProfile.this, jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditProfile.this, ViewProfile.class);
                        user = new String[]{user[0],etemail.getText().toString(), user[2], etlastName.getText().toString(), etfirstName.getText().toString()};
                        //test array
                        for(int x =0; x < user.length; x++){
                            Log.d("item", user[x]);
                        }
                        user_detail = new String[]{etaddress.getText().toString(), etcompany.getText().toString(), etmobile.getText().toString()};
                        //test array
                        for(int j =0; j < user_detail.length; j++){
                            Log.d("item", user_detail[j]);
                        }
                        intent.putExtra("user", user);
                        intent.putExtra("user_detail", user_detail);
                        startActivity(intent);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }

                }
            };
}
