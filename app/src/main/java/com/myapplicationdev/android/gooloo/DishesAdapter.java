package com.myapplicationdev.android.gooloo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.ViewHolder>{
    private List<DishesItem> listDish;
    private Context context;
    private String [] user;
    private String [] user_detail;
    private int mid; // for addShoppingCart.php
    private String postCode; //for addShoppingCart.php
    private int dishID; //for addShoppingCartDetails.php
    private String orderRef;
    private int order_id;
    private String image_name;
    private String company = "";

    public DishesAdapter(List<DishesItem> listDish, Context context, String[]userData, int mID, String pCode, String[]userDetail) {
        this.listDish = listDish;
        this.context = context;
        user = userData;
        mid = mID;
        postCode = pCode;
        user_detail = userDetail;
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
//        if(listOneDish.getDishImage().equals("")){
//            String photo_url = "http://ivriah.000webhostapp.com/gooloo/photo/" + listOneDish.getDishImage();
//            new DownloadImageTask(holder.ivImage).execute(photo_url);
//        }else{
//            holder.ivImage.setImageResource(R.drawable.logo);
//        }
        String photo_url = "http://ivriah.000webhostapp.com/gooloo/photos/" + listOneDish.getDishImage();
        Log.d("dish url", photo_url);
        Picasso.with(context).load(photo_url).error(R.drawable.logo).into(holder.ivImage);

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
            if(user_detail!=null || user_detail.length>0){
                company = user_detail[1];
            }else{
                company = "";
            }
            Log.d("userDetail", Arrays.toString(user_detail));
            //http://10.0.2.2/gooloo/
            //http://ivriah.000webhostapp.com/gooloo/gooloo/

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
                                final String price = tvPrice.getText().toString();
                                final double amount = Double.parseDouble(String.format("%.2f", Double.parseDouble(price.substring(1)) * number));
                                final String delivery_date = "Today";
                                final String delivery_time = "12:00-12:30";
                                final int user_id = Integer.parseInt(user[0]);
                                final StringBuilder sb = new StringBuilder();

                                RequestQueue queueOF = Volley.newRequestQueue(contextI); //get from
                                final String of = "order_ref=GL" + formattedDate;
                                String of_url ="http://ivriah.000webhostapp.com/gooloo/gooloo/getOrderRef.php?" + of;
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
                                                sb.append("&company=" + company);
                                                sb.append("&firstName=" + user[4]);
                                                sb.append("&lastName=" + user[3]);
                                                sb.append("&final=" + String.format("%.2f", Double.parseDouble(price.substring(1))));

                                                orderRef = "GL"+rpn;
                                                RequestQueue queue = Volley.newRequestQueue(contextI); //add to orders table
                                                String url ="http://ivriah.000webhostapp.com/gooloo/gooloo/add-cart.php?" + sb.toString();
                                                Log.d("url", url);
                                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {

                                                                RequestQueue queueSC = Volley.newRequestQueue(contextI); //add to shopping_cart table
                                                                String url ="http://ivriah.000webhostapp.com/gooloo/gooloo/addShoppingCart.php?customer_id="+ user_id +"&country_id=0&state_id=0&city_id=0&city_name=Singapore&delivery_date="+ delivery_date +"&delivery_time="+ delivery_time +"&m_id="+ mid +"&search_postcode="+ postCode +"&create_time="+ booking_time +"&update_time="+ booking_time;
                                                                Log.d("url", url);
                                                                StringRequest stringRequestSC = new StringRequest(Request.Method.GET, url,
                                                                        new Response.Listener<String>() {
                                                                            @Override
                                                                            public void onResponse(String response) {

                                                                                RequestQueue queueGSC = Volley.newRequestQueue(contextI); //get shopping_cart_id
                                                                                String url ="http://ivriah.000webhostapp.com/gooloo/gooloo/getShoppingCart.php";
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
                                                                                                    String url ="http://ivriah.000webhostapp.com/gooloo/gooloo/addShoppingCartDetails.php?shopping_cart_id="+ shpCartId +"&items_id="+ dishID +"&amount="+ number +"&create_time="+ booking_time +"&update_time="+ booking_time;
                                                                                                    Log.d("url", url);
                                                                                                    StringRequest stringRequestSCD = new StringRequest(Request.Method.GET, url,
                                                                                                            new Response.Listener<String>() {
                                                                                                                @Override
                                                                                                                public void onResponse(String response) {
                                                                                                                    RequestQueue queueGetOrderId = Volley.newRequestQueue(contextI);
                                                                                                                    String url = "http://ivriah.000webhostapp.com/gooloo/gooloo/getOrderByOrderId.php?" + of;
                                                                                                                    Log.d("url", url);
                                                                                                                    StringRequest stringRequestGOI = new StringRequest(Request.Method.GET, url,
                                                                                                                            new Response.Listener<String>() {
                                                                                                                                @Override
                                                                                                                                public void onResponse(String response) {
                                                                                                                                    JSONArray jsonArray1 = null;
                                                                                                                                    try {
                                                                                                                                        jsonArray1 = new JSONArray(response);
                                                                                                                                        int length = jsonArray1.length();
                                                                                                                                        Log.d("lenght", String.valueOf(length));
                                                                                                                                        JSONObject jsonObject1 = jsonArray1.getJSONObject(length);
                                                                                                                                        String orderID = jsonObject1.getString("id");
                                                                                                                                        RequestQueue queueAddOrderDetail = Volley.newRequestQueue(contextI);
                                                                                                                                        String getMethod = "order_id=" + orderID + "&item_id=" + dishID + "&m_id=" + mid + "&item_name=" + tvName.getText() + "&item_cn_name=" + tvCNName.getText() + "&amount=" + amount + "&count=" + count[0] + "&price=" + price.substring(1) + "&create_time=" + booking_time + "&update_time=" + booking_time + "&pick_count=" + count[0];
                                                                                                                                        Log.d("add url", getMethod);
                                                                                                                                        String url = "http://ivriah.000webhostapp.com/gooloo/gooloo/addOrderDetail.php?" + getMethod;
                                                                                                                                        Log.d("url", url);
                                                                                                                                        StringRequest stringRequestAOD = new StringRequest(Request.Method.GET, url,
                                                                                                                                                new Response.Listener<String>() {
                                                                                                                                                    @Override
                                                                                                                                                    public void onResponse(String response) {
                                                                                                                                                        Log.d("response", response.toString());
                                                                                                                                                        Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                                                                                                                                                    }
                                                                                                                                                }, new Response.ErrorListener() {
                                                                                                                                            @Override
                                                                                                                                            public void onErrorResponse(VolleyError error) {
                                                                                                                                                Log.d("item", error.toString()+"");
                                                                                                                                                Toast toast = Toast.makeText(context, error.toString(), Toast.LENGTH_LONG);
                                                                                                                                                toast.show();
                                                                                                                                            }
                                                                                                                                        });
                                                                                                                                        queueAddOrderDetail.add(stringRequestAOD);
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
                                                                                                                    queueGetOrderId.add(stringRequestGOI);
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

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
