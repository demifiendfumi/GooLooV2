package com.myapplicationdev.android.gooloo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.ViewHolder>{
    private List<DishesItem> listDish;
    private Context context;
    private String [] user;
    private int mid; // for addShoppingCart.php
    private String postCode; //for addShoppingCart.php
    private int dishID; //for addShoppingCartDetails.php

    public DishesAdapter(List<DishesItem> listDish, Context context, String[]userData, int mID, String pCode) {
        this.listDish = listDish;
        this.context = context;
        user = userData;
        mid = mID;
        postCode = pCode;
    }

    @Override
    public DishesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dishes_row, parent, false);
        return new DishesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final DishesItem listOneDish = listDish.get(position);
        dishID = listOneDish.getDishId();
        holder.tvName.setText(listOneDish.getDishName());
        holder.tvCNName.setText(String.valueOf(listOneDish.getDishCNName()));
        holder.tvPrice.setText("$" + listOneDish.getPrice());
        if(listOneDish.getDishImage().equals("Group Breakfast")){
            holder.ivImage.setImageResource(R.drawable.group_breakfast);
        }else if (listOneDish.getDishImage().equals("Catering Services")){
            holder.ivImage.setImageResource(R.drawable.catering_service);
        }else if (listOneDish.getDishImage().equals("Gooloo Selected @ Jurong West")){
            holder.ivImage.setImageResource(R.drawable.gooloo_selected);
        }else{
            holder.ivImage.setImageResource(R.drawable.logo);
        }

//        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("click add", "clicked");
//                AlertDialog.Builder myBuilder = new AlertDialog.Builder(context, R.style.AppTheme);
//                LayoutInflater inflater =
//                        (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View viewDialog = inflater.inflate(R.layout.input, null);
//                final int[] count = {0};
//                Log.d("count", String.valueOf(count[0]));
//                TextView tvDN =(TextView) viewDialog.findViewById(R.id.tvDishName);
//                final TextView tvCount =(TextView) viewDialog.findViewById(R.id.textViewCount);
//                Button btnMinus =(Button)viewDialog.findViewById(R.id. buttonMinus);
//                Button btnAdd =(Button)viewDialog.findViewById(R.id. buttonAdd);
//
//                Log.d("context log", String.valueOf(context));
//
//                myBuilder.setView(viewDialog);
//                myBuilder.setTitle("Number of orders");
//                myBuilder.setCancelable(true);
//                tvDN.setText(holder.tvName.getText() + "\n" + holder.tvCNName.getText());
//                Log.d("display alert", "displaying");
//                btnAdd.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        count[0] += 1;
//                        tvCount.setText(count[0]);
//                    }
//                });
//
//                btnMinus.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if(count[0] == 0){
//                            Toast.makeText(context, "You have 0 order", Toast.LENGTH_LONG).show();
//                        }else{
//                            count[0] -= 1;
//                            tvCount.setText(count[0]);
//                        }
//                    }
//                });
//
//                myBuilder.setPositiveButton("Add to cart", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(context, "Item is added to cart", Toast.LENGTH_LONG).show();
//                    }
//                });
//                myBuilder.setNegativeButton("Cancel",null);
//
//                AlertDialog myDialog = myBuilder.create();
//                Log.d("show dialog", "dialog");
//                myDialog.show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listDish.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvName, tvCNName, tvPrice;
        public ImageView ivImage;
        public Button btnAdd;
        public LinearLayout linearLayout;
        private Context contextI;

        public ViewHolder(View itemView) {
            super(itemView);
            contextI = itemView.getContext();
            ivImage = (ImageView)itemView.findViewById(R.id. ivImageDish);
            tvName = (TextView)itemView.findViewById(R.id. textViewDishName);
            tvCNName = (TextView)itemView.findViewById(R.id. textViewCNName);
            tvPrice = (TextView)itemView.findViewById(R.id. textViewPrice);
            btnAdd = (Button)itemView.findViewById(R.id. btnAdd);
            linearLayout = (LinearLayout) itemView.findViewById(R.id. linearLayout);
            Log.d("userData", Arrays.toString(user));

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("click add", "clicked");
                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(contextI);
                    LayoutInflater inflater =
                            (LayoutInflater)contextI.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewDialog = inflater.inflate(R.layout.input, null);
                    final int[] count = {0};
                    Log.d("count", String.valueOf(count[0]));
                    TextView tvDN =(TextView) viewDialog.findViewById(R.id.tvDishName);
                    final TextView tvCount =(TextView) viewDialog.findViewById(R.id.textViewCount);
                    Button btnMinus =(Button)viewDialog.findViewById(R.id. buttonMinus);
                    Button btnAdd =(Button)viewDialog.findViewById(R.id. buttonAdd);

                    myBuilder.setView(viewDialog);
                    myBuilder.setTitle("Number of orders");
                    myBuilder.setCancelable(false);
                    tvDN.setText(tvName.getText() + "\n" + tvCNName.getText());
                    Log.d("display alert", "displaying");
                    btnAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            count[0] += 1;
                            tvCount.setText(String.valueOf(count[0]));
                        }
                    });

                    btnMinus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(count[0] == 0){
                                Toast.makeText(context, "You have 0 order", Toast.LENGTH_LONG).show();
                            }else{
                                count[0] -= 1;
                                tvCount.setText(String.valueOf(count[0]));
                            }
                        }
                    });

                    myBuilder.setPositiveButton("Add to cart", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(count[0]== 0){
                                Toast.makeText(context, "Please add a item", Toast.LENGTH_LONG).show();
                            }else{
                                Date c = Calendar.getInstance().getTime();
                                System.out.println("Current time => " + c);

                                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String formattedDate = df.format(c);
                                final String booking_time = sdf.format(c);
                                final int number = count[0]; //item order
                                String price = tvPrice.getText().toString();
                                final double amount = Double.parseDouble(String.format("%.2f", Double.parseDouble(price.substring(1)) * number));
                                final String delivery_date = "Today";
                                final String delivery_time = "12:00-12:30";
                                final int user_id = Integer.parseInt(user[0]);
                                final StringBuilder sb = new StringBuilder();

                                RequestQueue queueOF = Volley.newRequestQueue(contextI); //get from
                                final String of = "order_ref=GL" + formattedDate;
                                String of_url ="http://10.0.2.2/gooloo/getOrderRef.php?" + of;
                                Log.d("of_url", of_url);
                                StringRequest strRequest = new StringRequest(Request.Method.GET, of_url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String rpn) {
                                                sb.append(of);
                                                sb.append(rpn);
                                                sb.append("&customer_id=" + user_id);
                                                sb.append("&delivery_date=" + delivery_date);
                                                sb.append("&delivery_time=" + delivery_time);
                                                sb.append("&amount=" + amount);
                                                sb.append("&booking_time=" + booking_time);

                                                RequestQueue queue = Volley.newRequestQueue(contextI); //add to orders table
                                                String url ="http://10.0.2.2/gooloo/add-cart.php?" + sb.toString();
                                                Log.d("url", url);
                                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {

                                                                RequestQueue queueSC = Volley.newRequestQueue(contextI); //add to shopping_cart table
                                                                String url ="http://10.0.2.2/gooloo/addShoppingCart.php?customer_id="+ user_id +"&country_id=0&state_id=0&city_id=0&city_name=Singapore&delivery_date="+ delivery_date +"&delivery_time="+ delivery_time +"&m_id="+ mid +"&search_postcode="+ postCode +"&create_time="+ booking_time +"&update_time="+ booking_time;
                                                                Log.d("url", url);
                                                                StringRequest stringRequestSC = new StringRequest(Request.Method.GET, url,
                                                                        new Response.Listener<String>() {
                                                                            @Override
                                                                            public void onResponse(String response) {

                                                                                RequestQueue queueGSC = Volley.newRequestQueue(contextI); //get shopping_cart_id
                                                                                String url ="http://10.0.2.2/gooloo/getShoppingCart.php";
                                                                                Log.d("url", url);
                                                                                StringRequest stringRequestGSC = new StringRequest(Request.Method.GET, url,
                                                                                        new Response.Listener<String>() {
                                                                                            @Override
                                                                                            public void onResponse(String response) {
                                                                                                try {
                                                                                                    JSONArray jsonArray = new JSONArray(response);
                                                                                                    JSONObject jsonObject =  jsonArray.getJSONObject(0);
                                                                                                    String shpCartId = jsonObject.getString("id");
                                                                                                    Log.d("shoppingCart_id", shpCartId);

                                                                                                    RequestQueue queueSCD = Volley.newRequestQueue(contextI); //add to shopping_cart_details table
                                                                                                    String url ="http://10.0.2.2/gooloo/addShoppingCartDetails.php?shopping_cart_id="+ shpCartId +"&items_id="+ dishID +"&amount="+ number +"&create_time="+ booking_time +"&update_time="+ booking_time;
                                                                                                    Log.d("url", url);
                                                                                                    StringRequest stringRequestSCD = new StringRequest(Request.Method.GET, url,
                                                                                                            new Response.Listener<String>() {
                                                                                                                @Override
                                                                                                                public void onResponse(String response) {
                                                                                                                    Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                                                                                                                }
                                                                                                            }, new Response.ErrorListener() {
                                                                                                        @Override
                                                                                                        public void onErrorResponse(VolleyError error) {
                                                                                                            Log.d("item", error.toString()+"");
                                                                                                            Toast toast = Toast.makeText(context, error.toString(), Toast.LENGTH_LONG);
                                                                                                            toast.show();
                                                                                                        }
                                                                                                    });

                                                                                                    queueSCD.add(stringRequestSCD);

                                                                                                } catch (JSONException e) {
                                                                                                    e.printStackTrace();
                                                                                                }
                                                                                            }
                                                                                        }, new Response.ErrorListener() {
                                                                                    @Override
                                                                                    public void onErrorResponse(VolleyError error) {
                                                                                        Log.d("item", error.toString()+"");
                                                                                        Toast toast = Toast.makeText(context, error.toString(), Toast.LENGTH_LONG);
                                                                                        toast.show();
                                                                                    }
                                                                                });

                                                                                queueGSC.add(stringRequestGSC);

                                                                            }
                                                                        }, new Response.ErrorListener() {
                                                                    @Override
                                                                    public void onErrorResponse(VolleyError error) {
                                                                        Log.d("item", error.toString()+"");
                                                                        Toast toast = Toast.makeText(context, error.toString(), Toast.LENGTH_LONG);
                                                                        toast.show();
                                                                    }
                                                                });

                                                                queueSC.add(stringRequestSC);

                                                            }
                                                        }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Log.d("item", error.toString()+"");
                                                        Toast toast = Toast.makeText(context, error.toString(), Toast.LENGTH_LONG);
                                                        toast.show();
                                                    }
                                                });

                                                queue.add(stringRequest);
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("item", error.toString()+"");
                                        Toast toast = Toast.makeText(context, error.toString(), Toast.LENGTH_LONG);
                                        toast.show();

                                    }
                                });
                                queueOF.add(strRequest);
//                                SELECT SUBSTRING(order_ref, 11, 6)
//                                FROM `orders` WHERE SUBSTRING(order_ref, 1, 10) = "GL20160311"
                            }
                        }
                    });
                    myBuilder.setNegativeButton("Cancel",null);

                    AlertDialog myDialog = myBuilder.create();
                    Log.d("show dialog", "dialog");
                    myDialog.show();
                }
            });
        }
    }

}
