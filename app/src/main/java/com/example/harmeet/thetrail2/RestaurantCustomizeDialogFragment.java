package com.example.harmeet.thetrail2;

/**
 * Created by Harmeet on 26-08-2016.
 */
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class RestaurantCustomizeDialogFragment extends DialogFragment {

    RecyclerView customizeView;

    AddToCartClicked mCallback;

    public interface AddToCartClicked{
        public void addToCart();
        public void updateQuantity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View rootView = inflater.inflate(R.layout.restaurant_customize, container, false);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        rootView.setMinimumWidth((int)(width*0.8));
        rootView.setMinimumHeight((int)(height*0.8));

        Bundle args = getArguments();
        RestaurantBean.Product product = (RestaurantBean.Product) args.getSerializable("product");

        ((TextView)rootView.findViewById(R.id.restaurant_customize_dish_name)).setText(product.productName);
        ((TextView)rootView.findViewById(R.id.restaurant_customize_dish_description)).setText(product.productDescription);
        customizeView = (RecyclerView)rootView.findViewById(R.id.restaurant_customize_list);
        customizeView.setLayoutManager(new LinearLayoutManager(getActivity()));
        customizeView.setAdapter(new RestaurantCustomizeAdapter(product,getFragmentManager()));

        rootView.findViewById(R.id.confirm_to_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback = (AddToCartClicked)getActivity();
                mCallback.addToCart();
                mCallback.updateQuantity();
                dismiss();
            }
        });
        rootView.findViewById(R.id.cancel_to_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mCallback = (AddToCartClicked)getActivity();
                //mCallback.addToCart();
                dismiss();
            }
        });

        return rootView;
    }

    public void updateOptionsText(HashMap<Integer, HashSet<Integer>> currentCustomization){
        Log.d("some","method");
        for(int i=0;i<customizeView.getChildCount();i++){
            RestaurantCustomizeAdapter.ViewHolder viewHolder = (RestaurantCustomizeAdapter.ViewHolder)customizeView.findViewHolderForAdapterPosition(i);
            int customizationId = ((RestaurantBean.Customization)viewHolder.itemView.getTag()).customizationId;
            if(currentCustomization.containsKey(customizationId)){
            //if(cartCustomizationId==customizationId) {
                HashSet<Integer> optionIds = currentCustomization.get(customizationId);

                String optionText="";
                Iterator<Integer> iterator = optionIds.iterator();
                ArrayList<RestaurantBean.Option> options = ((RestaurantBean.Customization)viewHolder.itemView.getTag()).options;
                while (iterator.hasNext()){
                    int nextId = iterator.next();
                    for(int j=0;j<options.size();j++){
                        if(nextId==options.get(j).optionId) {
                            optionText = optionText + options.get(j).optionName + ", ";
                            break;
                        }
                    }
                    //optionText = optionText.substring(0,optionText.length()-1);
                }
                optionText = optionText.substring(0,optionText.length()-2);
                viewHolder.options.setText(optionText);
                Log.d("changes",customizationId+"###"+optionIds.toString());
            }
        }
    }
}