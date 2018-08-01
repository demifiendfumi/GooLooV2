package com.myapplicationdev.android.gooloo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DishesActivity extends OptionMenu {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter ra;
    private List<DishesItem> listDishes;
    TextView tvResult;
    int m_id = 0;
    String [] data;
    String postal;
    String [] user_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes);
        Log.d("start", "starting");
        recyclerView = (RecyclerView)findViewById(R.id. rvLVDish);
        tvResult = (TextView)findViewById(R.id. textViewResSelect);
        recyclerView.setHasFixedSize(true);
        //get current context as parameter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        data = intent.getStringArrayExtra("userData");
        m_id = intent.getIntExtra("id", 0);
        postal = intent.getStringExtra("postal");
        user_detail = intent.getStringArrayExtra("user_detail");
        tvResult.setText(intent.getStringExtra("res_name"));

        listDishes = new ArrayList<>();
        Log.d("run load","running");
        loadRecyclerViewData();
        Log.d("run loaded","ran");
    }

    private void loadRecyclerViewData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(DishesActivity.this);
        String url ="http://ivriah.000webhostapp.com/gooloo/gooloo/get-dishes.php?m_id=" + m_id;
        //http://10.0.2.2/gooloo/
        //http://ivriah.000webhostapp.com/gooloo/gooloo/

        Log.d("url", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @TargetApi(Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            Log.d("check String", "" + response.toString());

                            Log.d("try", "running now");
                            JSONObject jsonObject = new JSONObject();
                            JSONArray jsonArray = new JSONArray(response);
                            Log.d("JSONArray", jsonArray.length() + "");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                DishesItem dish = new DishesItem(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("image"),
                                        jsonObject.getString("name"),
                                        jsonObject.getString("cn_name"),
                                        jsonObject.getString("price"),
                                        jsonObject.getString("category_name")
                                );
                                listDishes.add(dish);
                            }
                            //tvResult.setText(postal);
                            ra = new DishesAdapter(listDishes, getApplicationContext(), data, m_id, postal, user_detail);
                            recyclerView.setAdapter(ra);

                        } catch (JSONException e) {
                            Log.d("catch", "running now");
                            e.printStackTrace();
                            tvResult.setText("No dishes available");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("item", error.toString()+"");
                Toast toast = Toast.makeText(DishesActivity.this, "There's no internet connection", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        queue.add(stringRequest);


    }

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
            i.putExtra("user", data);
            if(user_detail!= null && user_detail.length>0){
                i.putExtra("user_detail", user_detail);
            }
            startActivity(i);
            return true;
        }else if (id == R.id.profileSelection) {
            Intent i = new Intent(this, ViewProfile.class);
            i.putExtra("user", data);
            if(user_detail!= null && user_detail.length>0){
                i.putExtra("user_detail", user_detail);
            }
            startActivity(i);
            Log.d("profile", "Profile Selected");
            return true;
        }else if (id == R.id.cartSelection) {
            Intent i = new Intent(this, ViewCart.class);
            i.putExtra("user", data);
            if(user_detail!= null && user_detail.length>0){
                i.putExtra("user_detail", user_detail);
            }
            startActivity(i);
            return true;
        }else if (id == R.id.orderSelection) {
            Intent i = new Intent(getBaseContext(), OrderPage.class);
            i.putExtra("user", data);
            if(user_detail!= null && user_detail.length>0){
                i.putExtra("user_detail", user_detail);
            }
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
