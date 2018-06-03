package com.myapplicationdev.android.gooloo;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.drm.ProcessedData;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter ra;

    private List<RestaurantItem> listItems;
    TextView tvResult;
    String postal;
    int id;
//    String resName;
//    String resLogo;
//    double resRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        Log.d("start", "starting");
        recyclerView = (RecyclerView)findViewById(R.id. rvLV);
        tvResult = (TextView)findViewById(R.id. textViewResult);
        recyclerView.setHasFixedSize(true);
        //get current context as parameter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        String user[] = intent.getStringArrayExtra("user");
        postal = intent.getStringExtra("postal_code");
        Log.d("store Postal", postal);

        listItems = new ArrayList<>();
        Log.d("run load","running");
        loadRecyclerViewData();
        Log.d("run loaded","ran");


    }

    private void loadRecyclerViewData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(RestaurantActivity.this);
        String url ="http://10.0.2.2/gooloo/get-restaurants.php?postcode=" + postal;
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
                                RestaurantItem item = new RestaurantItem(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("logo"),
                                        jsonObject.getString("name"),
                                        jsonObject.getDouble("rating")
                                );
                                listItems.add(item);
                            }
                            tvResult.setText(postal);
                            Log.d("arraylist", listItems.size() + "");
                            Log.d("arraylist 1", listItems.get(0).toString());
                            Log.d("listItems", listItems.size()+"");
                            ra = new RestaurantAdapter(listItems, getApplicationContext());
                            recyclerView.setAdapter(ra);

                        } catch (JSONException e) {
                            Log.d("catch", "running now");
                            e.printStackTrace();
                            tvResult.setText("Restaurant not found");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("item", error.toString()+"");
                Toast toast = Toast.makeText(RestaurantActivity.this, "", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        queue.add(stringRequest);


    }
}
