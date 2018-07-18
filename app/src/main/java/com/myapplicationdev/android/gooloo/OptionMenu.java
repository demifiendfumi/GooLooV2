package com.myapplicationdev.android.gooloo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class OptionMenu extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //Step 2:
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.homeSelection) {
            Intent i = new Intent(this, HomeActivity.class);
            //i.putExtra("user", user);
            startActivity(i);
            return true;
        }else if (id == R.id.profileSelection) {
//            Intent i = new Intent(getBaseContext(), HomeActivity.class);
//            startActivity(i);
            Log.d("profile", "Profile Selected");
            return true;
        }else if (id == R.id.cartSelection) {
            Intent i = new Intent(this, ViewCart.class);
            startActivity(i);
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
