package com.myapplicationdev.android.gooloo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

public class HomeActivity extends OptionMenu {

    TextView tvFirstName, tvLastName;
    ImageView ivProfile;
    EditText etPostalCode;
    Button btnSearch;
    int postal_code;
    String postal, firstName, lastName;
    String user[];

//    private DrawerLayout mDrawerLayout;
//    private ActionBarDrawerToggle mToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvFirstName = (TextView) findViewById(R.id.tvFirstName);
        tvLastName = (TextView) findViewById(R.id.tvLastName);
        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        etPostalCode = (EditText) findViewById(R.id.edtPostalCode);
        btnSearch = (Button) findViewById(R.id.btnSearch);

//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mToggle = new ActionBarDrawerToggle (this,mDrawerLayout, R.string.open, R.string.close);
//
//        mDrawerLayout.addDrawerListener(mToggle);
//        mToggle.syncState();
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        // Get intents
        Intent intent = getIntent();
        user = intent.getStringArrayExtra("user");

        //Extract data from user
        firstName = user[4];
        lastName = user[3];

        tvFirstName.setText(firstName);
        tvLastName.setText(lastName);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postal = etPostalCode.getText().toString();
                Log.d("postal", postal);
                try {
                    postal_code = Integer.parseInt(postal);
                    Log.d("postal", postal_code+"");
                    if (postal_code == (int) postal_code && postal.length() == 6) {

                        Intent j = new Intent(HomeActivity.this, RestaurantActivity.class);
                        j.putExtra("user", user);
                        j.putExtra("postal_code", postal);
                        startActivity(j);
                    }
                }catch (NumberFormatException e){
                    Toast toast = Toast.makeText(HomeActivity.this, "Sorry, but there are no restaurants found for this postcode.", Toast.LENGTH_LONG);
                    toast.show();
                }
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
            Toast.makeText(this, "You are at the Home Page", Toast.LENGTH_SHORT).show();
            return true;
        }else if (id == R.id.profileSelection) {
            Intent i = new Intent(this, ViewProfile.class);
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
//            Intent i = new Intent(getBaseContext(), ViewCart.class);
//            startActivity(i);
            Log.d("view order", "Order selected");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(mToggle.onOptionsItemSelected(item)){
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        int id = item.getItemId();
//
//        if (id == R.id.nav_account){
//
//            Toast.makeText(this, "account", Toast.LENGTH_SHORT).show();
//
//
//        }
//        if(id == R.id.nav_team){
//
//            Intent intent = new Intent(this,ManageTeamActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            this.startActivity(intent);
//
//        }
//        if(id == R.id.nav_order){
//
//            Toast.makeText(this, "order", Toast.LENGTH_SHORT).show();
//
//        }
//        if(id == R.id.nav_logout){
//
//            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
//
//        }
//        return false;
//    }
}
