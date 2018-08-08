package com.myapplicationdev.android.gooloo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder>{
    private List<RestaurantItem> listRes;
    private Context context;
    private AdapterView.OnItemClickListener listener;
    private String [] user;
    private String pCode;
    private String[] user_detail;

    public RestaurantAdapter(List<RestaurantItem> listRes, Context context, String[]userData, String postCode, String[]userDetail) {
        this.listRes = listRes;
        this.context = context;
        user = userData;
        pCode = postCode;
        user_detail = userDetail;
    }

    public void setClickListener(AdapterView.OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final RestaurantItem listOneRes = listRes.get(position);
//        String logo = "res/drawable/" + listOneRes.getImageName();
//        File file = new File(logo);
//        Log.d("file", String.valueOf(file));
//        Bitmap bm;
//        if (file.exists()){
//
//            bm = BitmapFactory.decodeFile(logo);
//        }
//        else{// use the default icon
//            bm = BitmapFactory.decodeFile("res/drawable/logo.png");
//        }
//        holder.ivLogo.setImageBitmap(bm);
        //holder.tvImage.setText(listOneRes.getImageName());

        holder.tvName.setText(listOneRes.getResName());
        holder.tvRating.setText(String.valueOf(listOneRes.getResRating()));
        String photo_url = "http://ivriah.000webhostapp.com/gooloo/photos/" + listOneRes.getImageName();
        Log.d("photo_url", photo_url);
//        if(listOneRes.getImageName().equals("")){
//            String photo_url = "http://ivriah.000webhostapp.com/gooloo/photo/" + listOneRes.getImageName();
//            new DownloadImageTask(holder.ivLogo).execute(photo_url);
//        }else{
//            holder.ivLogo.setImageResource(R.drawable.logo);
//        }
        Picasso.with(context).load(photo_url).error(R.drawable.logo).into(holder.ivLogo);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You clicked " + listOneRes.getResName(), Toast.LENGTH_LONG).show();
                Intent i = new Intent(context, DishesActivity.class);
                i.putExtra("id", listOneRes.getId());
                i.putExtra("res_name", listOneRes.getResName());
                i.putExtra("userData", user);
                i.putExtra("postal", pCode);
                i.putExtra("user_detail", user_detail);
                Log.d("user_data", Arrays.toString(user));
                Log.d("Res id", String.valueOf(listOneRes.getId()));
                Log.d("postal", pCode);
                context.startActivity(i);
            }
        });
//        String imageName = listOneRes.getImageName();
//        Context context = holder.ivLogo.getContext();
//        int id = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
//        Log.d("logo", String.valueOf(id));
//        holder.ivLogo.setImageResource(id);
    }

    @Override
    public int getItemCount() {
        return listRes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvName, tvRating;
        public ImageView ivLogo;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ivLogo = (ImageView)itemView.findViewById(R.id. ivImage);
            tvName = (TextView)itemView.findViewById(R.id. textViewName);
            tvRating = (TextView)itemView.findViewById(R.id. textViewRating);
            linearLayout = (LinearLayout)itemView.findViewById(R.id. linearLayout);
        }
    }
}
