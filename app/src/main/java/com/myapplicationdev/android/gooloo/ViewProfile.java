package com.myapplicationdev.android.gooloo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewProfile extends AppCompatActivity {
    ImageView ivProfile;
    TextView firstName, lastName, email, mobile, address, company;
    Button btnBack, btnEdit;
    String user[];

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

        Intent i = getIntent();
        user = i.getStringArrayExtra("user");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

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
//            Intent i = new Intent(getBaseContext(), ViewCart.class);
//            startActivity(i);
            Log.d("view order", "Order selected");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
