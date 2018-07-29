package com.myapplicationdev.android.gooloo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class RecentFragment extends Fragment {
    ListView lv_recent;
    ArrayList<String> al;
    ArrayAdapter<String> aa;
    String user[];
    String [] recent_order;

    public RecentFragment() {
        // Required empty public constructor
    }

    public static RecentFragment newInstance(){
        RecentFragment fragment = new RecentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recent, container, false);
        OrderPage activity = (OrderPage) getActivity();
        user = activity.getUser();

        lv_recent = (ListView)rootView.findViewById(R.id.lv_recent);
        al = new ArrayList<String>();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        //http://10.0.2.2/gooloo/
        //http://ivriah.000webhostapp.com/gooloo/gooloo/
        String url = "http://10.0.2.2/gooloo/geRecentOrder.php?customer_id=" + user[0];
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArr = new JSONArray(response);
                            for(int i = 0; i < jsonArr.length(); i++){
                                JSONObject jsonObj = jsonArr.getJSONObject(i);
                                String aOId = jsonObj.getString("id");
                                String order_ref = jsonObj.getString("order_ref");
                                String delivery_date = jsonObj.getString("delivery_date");
                                String delivery_time = jsonObj.getString("delivery_time");
                                String amount = jsonObj.getString("amount");
                                recent_order = new String[]{aOId, order_ref, delivery_date, delivery_time, amount};

                                al.add(recent_order.toString());

                                aa = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, al);
                                lv_recent.setAdapter(aa);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("RecentFragment","Unsuccessful retriever");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("RecentFragment", error.toString()+"");
                Toast toast = Toast.makeText(getActivity(), ""+error.toString(), Toast.LENGTH_LONG);
                toast.show();
            }
        });

        return rootView;
    }

    @Override
    public String toString() {
        return "Order Ref: " + recent_order[1] + "\n"
                + "Delivery Date: " + recent_order[2] + "\n"
                + "Delivery Time: " + recent_order[3] + "\n"
                + "Amount: $" + recent_order[4];
    }

}
