package com.myapplicationdev.android.gooloo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CartAdapter extends ArrayAdapter<Cart>{

    private ArrayList<Cart> carts;
    private Context context;
    private TextView tvDishName;
    private TextView tvQuantity;
    private TextView tvPrice;
    private TextView tvAmount;

    public CartAdapter(Context context, int resource, ArrayList<Cart> objects){
        super(context, resource, objects);
        carts = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.cart_row_item, parent, false);

        tvDishName = (TextView) rowView.findViewById(R.id.txtViewDishName);
        tvQuantity = (TextView) rowView.findViewById(R.id.txtViewQuantity);
        tvPrice = (TextView)rowView.findViewById(R.id. txtViewPrice);
        tvAmount = rowView.findViewById(R.id.txtAmount);

        Cart currentItem = carts.get(position);

        tvDishName.setText(currentItem.getDish_name());
        Log.d("dish name", currentItem.getDish_name());
        tvQuantity.setText(String.valueOf(currentItem.getQuantity()));
        Log.d("quantity", String.valueOf(currentItem.getQuantity()));
        tvPrice.setText("$"+String.format("%.2f", currentItem.getPrice()));
        Log.d("price", String.valueOf(currentItem.getPrice()));
        Double totalPrice = currentItem.getPrice()*currentItem.getQuantity();
        String price = String.format("%.2f", totalPrice);
        tvAmount.setText("$"+price);
        Log.d("quantity", price);

        return rowView;
    }
}
