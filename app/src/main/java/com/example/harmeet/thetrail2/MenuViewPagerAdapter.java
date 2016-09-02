package com.example.harmeet.thetrail2;

import android.app.Activity;
import android.content.Context;
import android.graphics.ColorFilter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Harmeet on 23-08-2016.
 */
public class MenuViewPagerAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<RestaurantBean.RestaurantTab> mTabs;
    Activity mActivity;
    public RestaurantCart mCart;

    public interface MenuViewPagerAdapterMethods{
        public void customize(View productView);
    }

    MenuViewPagerAdapterMethods callback;

    public MenuViewPagerAdapter(Context context, ArrayList<RestaurantBean.RestaurantTab> tabs, RestaurantCart cart, Activity activity) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mTabs = tabs;
        mCart = cart;
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (View)object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        int quantity;
        View itemView = mLayoutInflater.inflate(R.layout.restaurant_menu_layout,container,false);
        LinearLayout menuLayout = (LinearLayout) itemView.findViewById(R.id.restaurant_menu_linear_layout);
        RestaurantBean.RestaurantTab tab = mTabs.get(position);
        ArrayList<RestaurantBean.RestaurantDishCategory> categories = tab.categories;
        for(int i=0;i<categories.size();i++)
        {
            //add sticky header for category here
            View stickyView = mLayoutInflater.inflate(R.layout.sticky_cardview_menu,menuLayout,false);
            ((TextView)stickyView.findViewById(R.id.sticky_menu_text)).setText(categories.get(i).categoryName);
            menuLayout.addView(stickyView);
            ArrayList<RestaurantBean.Product> products = categories.get(i).products;
            for(int j=0;j<products.size();j++){

                //add products here
                final RestaurantBean.Product product = products.get(j);
                quantity = 0;
                for(int k=0;k<mCart.cartItems.size();k++) {
                    if (product.productId == mCart.cartItems.get(k).productId) {
                        quantity += mCart.cartItems.get(k).quantity;
                    }
                }



                final View productView = mLayoutInflater.inflate(R.layout.restaurant_menu_product,menuLayout, false);
                productView.setTag(product);
                ((TextView)productView.findViewById(R.id.dish_title)).setText(product.productName);
                ((TextView)productView.findViewById(R.id.dish_base_price)).setText("Rs. "+product.basePrice);
                ((TextView)productView.findViewById(R.id.dish_description)).setText(product.productDescription);
                ((TextView)productView.findViewById(R.id.dish_quantity)).setText(quantity+"");
                /*productView.findViewById(R.id.add_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(product.isCustomizable){
                            //callback = (MenuViewPagerAdapterMethods) mActivity;
                            if(callback==null)
                                Log.d("callback","null");
                            //callback.customize(productView);
                        }
                        else {

                        }
                    }
                });*/

                if(product.isVeg)
                    ((ImageView)productView.findViewById(R.id.dish_isveg)).setImageResource(R.drawable.veg);
                else
                    ((ImageView)productView.findViewById(R.id.dish_isveg)).setImageResource(R.drawable.nonveg);
                Picasso.with(mContext).load(product.productImageLink).into((ImageView) productView.findViewById(R.id.dish_image));
                menuLayout.addView(productView);
            }
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position).tabName;
    }
}
