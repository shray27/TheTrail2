package com.example.harmeet.thetrail2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CartFragment extends Fragment {

    CartFragmentMethods callback;

    public interface CartFragmentMethods{
        public RestaurantCart getCart();
    }

    RecyclerView recyclerView;
    CartProductRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        callback = (CartFragmentMethods)getActivity();

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.cart_fragment_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartProductRecyclerViewAdapter(callback.getCart());
        recyclerView.setAdapter(adapter);
        return view;
    }
}
