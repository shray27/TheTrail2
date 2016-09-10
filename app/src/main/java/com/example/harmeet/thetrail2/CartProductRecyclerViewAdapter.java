package com.example.harmeet.thetrail2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Harmeet on 31-08-2016.
 */
public class CartProductRecyclerViewAdapter extends RecyclerView.Adapter<CartProductRecyclerViewAdapter.ViewHolder> {

    RestaurantCart mCart;

    public CartProductRecyclerViewAdapter(RestaurantCart cart) {
        mCart = cart;
    }

    @Override
    public CartProductRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cart_product_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartProductRecyclerViewAdapter.ViewHolder holder, int position) {
        RestaurantCart.Product product = mCart.cartItems.get(position);
        holder.productQuantity.setText(product.quantity+"");
    }

    @Override
    public int getItemCount() {
        return mCart.cartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{



        TextView productName, productQuantity;
        ImageView productRemove;
        public ViewHolder(View itemView) {
            super(itemView);
            productName = (TextView)  itemView.findViewById(R.id.cart_product_name);
            productQuantity = (TextView) itemView.findViewById(R.id.cart_product_quantity);
            productRemove = (ImageView) itemView.findViewById(R.id.remove_cart_product);
            productRemove.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            removeAt(getAdapterPosition());
        }
    }

    public void removeAt(int position) {
        mCart.cartItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mCart.cartItems.size());
    }
}
