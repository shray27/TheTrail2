package com.example.harmeet.thetrail2;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Harmeet on 26-08-2016.
 */
public class RestaurantCustomizeAdapter extends RecyclerView.Adapter<RestaurantCustomizeAdapter.ViewHolder> {

    RestaurantBean.Product mProduct;    //product object containing product details
    ArrayList<RestaurantBean.Customization> mCustomizations;    //Customization array list containing array list of customization object
    FragmentManager mFragmentManager;

    public RestaurantCustomizeAdapter(RestaurantBean.Product product, FragmentManager fragmentManager) {
        mProduct = product;
        mCustomizations = product.customizations;
        mFragmentManager = fragmentManager;
    }

    @Override
    public int getItemCount() {
        return mCustomizations.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_customize_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.name.setText(mCustomizations.get(position).name);
        holder.view.setTag(mCustomizations.get(position));
        //holder.itemView.setTag(mProduct);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("method","called");
                RestaurantCustomizeOptionDialogFragment restaurantCustomizeOptionDialogFragment = new RestaurantCustomizeOptionDialogFragment();
                Bundle args = new Bundle();
                args.putSerializable("customization",mCustomizations.get(position));
                restaurantCustomizeOptionDialogFragment.setArguments(args);
                restaurantCustomizeOptionDialogFragment.show(mFragmentManager,mCustomizations.get(position).customizationId+"");
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView name,options,price;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            name = (TextView) itemView.findViewById(R.id.customization_name);
            options = (TextView) itemView.findViewById(R.id.customization_options);
            price = (TextView) itemView.findViewById(R.id.customization_price);
        }
    }
}
