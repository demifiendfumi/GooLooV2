package com.myapplicationdev.android.gooloo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CheckOut extends AppCompatActivity {
    TextView txtMsg;
    Button btnHome;
    String user[];
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        txtMsg = findViewById(R.id. txtMsg);
        btnHome = findViewById(R.id. btnHome);

        Intent i = getIntent();
        user = i.getStringArrayExtra("user");
        msg = i.getStringExtra("msg");
        txtMsg.setText(msg);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckOut.this, HomeActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
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
            Intent i = new Intent(getBaseContext(), ViewProfile.class);
            i.putExtra("user", user);
            startActivity(i);
            Log.d("profile", "Profile Selected");
            return true;
        }else if (id == R.id.cartSelection) {
            Intent i = new Intent(this, ViewCart.class);
            i.putExtra("user", user);
            startActivity(i);
            return true;
        }else if (id == R.id.orderSelection) {
            Intent i = new Intent(getBaseContext(), OrderPage.class);
            startActivity(i);
            Log.d("view order", "Order selected");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
