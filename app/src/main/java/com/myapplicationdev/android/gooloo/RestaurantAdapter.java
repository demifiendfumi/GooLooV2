package com.myapplicationdev.android.gooloo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder>{
    private List<RestaurantItem> listRes;
    private Context context;

    public RestaurantAdapter(List<RestaurantItem> listRes, Context context) {
        this.listRes = listRes;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RestaurantItem listOneRes = listRes.get(position);
         holder.tvImage.setText(listOneRes.getImageName());
         holder.tvName.setText(listOneRes.getResName());
         holder.tvRating.setText(String.valueOf(listOneRes.getResRating()));
    }

    @Override
    public int getItemCount() {
        return listRes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvImage,tvName, tvRating;

        public ViewHolder(View itemView) {
            super(itemView);
            tvImage = (TextView)itemView.findViewById(R.id. textViewImage);
            tvName = (TextView)itemView.findViewById(R.id. textViewName);
            tvRating = (TextView)itemView.findViewById(R.id. textViewRating);
        }
    }
}
