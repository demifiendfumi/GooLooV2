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

public class DishesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter ra;
    private List<DishesItem> listDishes;
    TextView tvResult;
    int m_id = 0;
    String [] data;
    String postal;

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
        String url ="http://10.0.2.2/gooloo/get-dishes.php?m_id=" + m_id;
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
                            ra = new DishesAdapter(listDishes, getApplicationContext(), data, m_id, postal);
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
}
