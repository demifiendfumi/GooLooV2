package com.myapplicationdev.android.gooloo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    TextView tvFirstName, tvLastName;
    ImageView ivProfile;
    EditText etPostalCode;
    Button btnSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvFirstName = (TextView) findViewById(R.id.tvFirstName);
        tvLastName = (TextView) findViewById(R.id.tvLastName);
        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        etPostalCode = (EditText) findViewById(R.id.edtPostalCode);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        // Get intents
        Intent intent = getIntent();
        final String user[] = intent.getStringArrayExtra("user");

        //Extract data from user
        String firstName = user[4];
        String lastName = user[3];

        tvFirstName.setText(firstName);
        tvLastName.setText(lastName);

        final int postal_code = Integer.parseInt(etPostalCode.getText().toString());
        final String check_postal = etPostalCode.getText().toString();


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(postal_code == (int)postal_code && check_postal.length() == 6){

                    Intent j = new Intent(HomeActivity.this, RestaurantActivity.class);
                    j.putExtra("user", user);
                    j.putExtra("postal_code", postal_code);

                    startActivity(j);
                }else{
                    Toast toast = Toast.makeText(HomeActivity.this, "invalid postal code", Toast.LENGTH_LONG);
                    toast.show();
                }


            }
        });



    }
}
