package com.example.harmeet.thetrail2;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MenuFragment extends Fragment {

    public interface MenuFragmentMethods{
        public RestaurantCart getCart();
        public void restore();
    }

    public MenuFragment() {
        // Required empty public constructor
    }

    MenuFragmentMethods mCallback;
    ViewPager viewPager;
    MenuViewPagerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mCallback = (MenuFragmentMethods) getActivity();
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_menu, container, false);
        viewPager = (ViewPager)view.findViewById(R.id.menu_viewpager);
        adapter = new MenuViewPagerAdapter(getContext(),((RestaurantActivity)getActivity()).bean.getTabs(),mCallback.getCart(),getActivity());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCallback.restore();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.menu_tabs);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    public void cartDataChanged(){
        adapter = new MenuViewPagerAdapter(getContext(),((RestaurantActivity)getActivity()).bean.getTabs(),mCallback.getCart(),getActivity());
        viewPager.setAdapter(adapter);
    }
}
