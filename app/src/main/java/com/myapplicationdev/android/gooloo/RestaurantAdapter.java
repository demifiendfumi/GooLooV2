package com.myapplicationdev.android.gooloo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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

import java.io.File;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder>{
    private List<RestaurantItem> listRes;
    private Context context;
    private AdapterView.OnItemClickListener listener;

    public RestaurantAdapter(List<RestaurantItem> listRes, Context context) {
        this.listRes = listRes;
        this.context = context;
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
        if(listOneRes.getResName().equals("Group Breakfast")){
            holder.ivLogo.setImageResource(R.drawable.group_breakfast);
        }else if (listOneRes.getResName().equals("Catering Services")){
            holder.ivLogo.setImageResource(R.drawable.catering_service);
        }else if (listOneRes.getResName().equals("Gooloo Selected @ Jurong West")){
            holder.ivLogo.setImageResource(R.drawable.gooloo_selected);
        }else{
            //holder.ivLogo.setImageResource(R.drawable.logo);
        }
        /*
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You clicked " + listOneRes.getResName(), Toast.LENGTH_LONG).show();
                Intent i = new Intent(context, DishesActivity.class);
                i.putExtra("id", listOneRes.getId());
                i.putExtra("res_name", listOneRes.getResName());
                Log.d("Res id", String.valueOf(listOneRes.getId()));
                context.startActivity(i);
            }
        });
        */
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

        public TextView tvName, tvRating, tvImage;
        public ImageView ivLogo;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ivLogo = (ImageView)itemView.findViewById(R.id. ivImage);
            tvName = (TextView)itemView.findViewById(R.id. textViewName);
            tvRating = (TextView)itemView.findViewById(R.id. textViewRating);
            linearLayout = (LinearLayout)itemView.findViewById(R.id. linearLayout);
            //tvImage = (TextView)itemView.findViewById(R.id. textViewImage);

        }
    }
}
