package com.myapplicationdev.android.gooloo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ViewCart extends OptionMenu {

    TextView txtDish, txtquantity, txtPrice, txtTotal, txtAmount;
    Button btnCheckout;
    ListView lv;
    ArrayAdapter aa;
    ArrayList<Cart> carts = new ArrayList<Cart>();
    double total;
    String user[];
    int c_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        lv = (ListView)findViewById(R.id. listView);
        txtDish = (TextView)findViewById(R.id. textViewDish_Name);
        txtquantity = (TextView)findViewById(R.id. textViewQuantity);
        txtPrice = (TextView)findViewById(R.id. textViewPrice);
        txtTotal = findViewById(R.id. textViewTotal_Price);
        txtAmount = findViewById(R.id. textViewAmount);
        btnCheckout = (Button)findViewById(R.id. btnCheckOut);

        Intent intent = getIntent();
        user = intent.getStringArrayExtra("user");
        c_id = Integer.parseInt(user[0]);

        aa = new CartAdapter(ViewCart.this, R.layout.cart_row_item, carts);
        aa.clear();
        lv.setAdapter(aa);

        HttpRequest request = new HttpRequest
                ("http://ivriah.000webhostapp.com/gooloo/gooloo/get-cart.php?c_id=" + c_id);
        //http://10.0.2.2/gooloo/
        //http://ivriah.000webhostapp.com/gooloo/gooloo/
        Log.d("url", "http://ivriah.000webhostapp.com/gooloo/gooloo/get-cart.php?c_id=" + c_id);
        request.setOnHttpResponseListener(mHttpResponseListener);
        request.setMethod("GET");
        request.execute();

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //order_ref
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                String formattedDate = df.format(c);
                String order_ref = "GL"+formattedDate;

                HttpRequest requestCO = new HttpRequest
                        ("http://ivriah.000webhostapp.com/gooloo/gooloo/checkOrder.php?order_ref="+ order_ref +"&c_id=" + c_id);
                Log.d("url", "http://ivriah.000webhostapp.com/gooloo/gooloo/checkOrder.php?order_ref="+ order_ref +"&c_id=" + c_id);
                requestCO.setOnHttpResponseListener(mHttpResponseCheckOut);
                requestCO.setMethod("GET");
                requestCO.execute();
            }
        });
    }

    private HttpRequest.OnHttpResponseListener mHttpResponseListener =
            new HttpRequest.OnHttpResponseListener() {
                @Override
                public void onResponse(String response){

                    // process response here
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonObj = jsonArray.getJSONObject(i);
                            String name = jsonObj.getString("name");
                            double price = jsonObj.getDouble("price");
                            int quantity = jsonObj.getInt("amount");
                            double amount = price * quantity;
                            total += amount;
                            Cart cart = new Cart(name,quantity, price);
                            carts.add(cart);
                        }
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }

                    aa.notifyDataSetChanged();
                    txtTotal.setText("Total Price: $"+String.format("%.2f", total));
                }
            };

    private HttpRequest.OnHttpResponseListener mHttpResponseCheckOut =
            new HttpRequest.OnHttpResponseListener() {
                @Override
                public void onResponse(String response){

                    // process response here
                    try {
                        String msg = response;
                        Toast.makeText(ViewCart.this, msg, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ViewCart.this, CheckOut.class);
                        i.putExtra("msg", msg);
                        i.putExtra("user", user);
                        startActivity(i);
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
            startActivity(i);
            return true;
        }else if (id == R.id.profileSelection) {
            Intent i = new Intent(this, ViewProfile.class);
            i.putExtra("user", user);
            startActivity(i);
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
