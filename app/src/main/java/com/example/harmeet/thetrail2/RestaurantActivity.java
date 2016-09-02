package com.example.harmeet.thetrail2;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.harmeet.thetrail2.Utilities.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class RestaurantActivity extends AppCompatActivity implements RestaurantCustomizeOptionDialogFragment.ConfirmClicked,
        RestaurantCustomizeDialogFragment.AddToCartClicked,
        MenuFragment.MenuFragmentMethods,
        CartFragment.CartFragmentMethods,
        MenuViewPagerAdapter.MenuViewPagerAdapterMethods{


    int currentProductId;
    TextView currentQuantityView;
    HashMap<Integer, HashSet<Integer>> currentCustomization = new HashMap<Integer, HashSet<Integer>>();
    //HashSet<Integer> allOptionList = new HashSet<Integer>();

    RestaurantCustomizeDialogFragment restaurantCustomizeDialogFragment;

    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private AHBottomNavigation bottomNavigation;
    RestaurantBean bean = null;
    RestaurantCart cart;
    MenuFragment menuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        new loadMenu().execute("5");
    }

    public void initToolbar(){
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Offers", R.drawable.ic_apps_black_24dp, R.color.color_tab_1);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Menu", R.drawable.ic_maps_local_bar, R.color.color_tab_2);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Reviews", R.drawable.ic_rate_review_black_24dp, R.color.color_tab_3);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Cart", R.drawable.ic_shopping_cart_black_24dp, R.color.color_tab_3);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("Profile", R.drawable.ic_perm_identity_black_24dp, R.color.color_tab_3);

        final FloatingActionButton addReviewButton = (FloatingActionButton) findViewById(R.id.add_review_floating_button);
        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigationItems.add(item4);
        bottomNavigationItems.add(item5);

        bottomNavigation.hideBottomNavigation(false);
        bottomNavigation.restoreBottomNavigation(true);

        bottomNavigation.addItems(bottomNavigationItems);

        bottomNavigation.setAccentColor(Color.parseColor("#9C27B0"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));

        bottomNavigation.setForceTitlesDisplay(true);

        menuFragment = new MenuFragment();

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if(position==1 && !wasSelected){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,menuFragment).commit();
                    addReviewButton.setVisibility(View.GONE);
                }
                else if (position==2 && !wasSelected){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,new ReviewFragment()).commit();
                    addReviewButton.setVisibility(View.VISIBLE);
                }
                else if (position==3 && !wasSelected){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,new CartFragment()).commit();
                    addReviewButton.setVisibility(View.GONE);
                }
                return true;
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        //collapsingToolbarLayout.setTitle(bean.storeName);
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(bean.storeName);
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
    }

    public void customize(View view) {
        View fView = (View)view.getParent().getParent().getParent();
        RestaurantBean.Product product = (RestaurantBean.Product) fView.getTag();
        //cart.cartCustomization.options.clear();
        currentCustomization.clear();
        //allOptionList.clear();
        currentProductId = product.productId;
        currentQuantityView = (TextView)fView.findViewById(R.id.dish_quantity);
        if(product.isCustomizable) {
            restaurantCustomizeDialogFragment = new RestaurantCustomizeDialogFragment();
            Bundle args = new Bundle();
            args.putSerializable("product", product);

            restaurantCustomizeDialogFragment.setArguments(args);

            restaurantCustomizeDialogFragment.show(getSupportFragmentManager(), product.productId + "");
        }
        else {
            addToCart();
            updateQuantity();
        }
    }

    public void customize_remove(View view) {
        View fView = (View)view.getParent().getParent().getParent();
        RestaurantBean.Product product = (RestaurantBean.Product) fView.getTag();
        currentProductId = product.productId;
        currentCustomization.clear();
        if(product.isCustomizable){

        }
        else {

        }
    }


    @Override
    public void addToCurrentCustomization(int customizationId, HashSet<Integer> optionList) {
        currentCustomization.put(customizationId,optionList);
        //cart.cartCustomization.customizationId = customizationId;
        //cart.cartCustomization.options = optionList;
        Log.d("data=",currentProductId + "=====" +customizationId+"====="+optionList.toString());
        restaurantCustomizeDialogFragment.updateOptionsText(currentCustomization);
        //Iterator<Integer> iterator = optionList.iterator();
        //while (iterator.hasNext())
            //allOptionList.add(iterator.next());
        Log.d("current",currentCustomization.toString());
    }

    @Override
    public void addToCart() {
        Log.d("before data",currentCustomization.toString());
        cart.addProduct(currentProductId,currentCustomization);
        /*Log.d("before data",currentProductId+allOptionList.toString());
        cart.addProduct(currentProductId,allOptionList);

        Log.d("cart",cart.cartItems.size()+"");*/
        for(int i=0;i<cart.cartItems.size();i++) {
            int productId = cart.cartItems.get(i).productId;
            int quantity = cart.cartItems.get(i).quantity;
            HashSet<Integer> optionsIds = cart.cartItems.get(i).optionsId;
            //HashMap<Integer,HashSet<Integer>> options = cart.cartItems.get(i).customization;
            Log.d("data", productId + "----" + quantity + "----" + optionsIds.toString());
        }
        //menuFragment.cartDataChanged();
        //int quantity = Integer.parseInt(((TextView)currentView.findViewById(R.id.dish_quantity)).getText().toString());
        //((TextView)currentView.findViewById(R.id.dish_quantity)).setText(quantity++);
    }

    @Override
    public void updateQuantity() {
        int quantity = Integer.parseInt((currentQuantityView).getText().toString());
        quantity++;
        (currentQuantityView).setText(quantity + "");
    }

    public RestaurantCart getCart(){
        return cart;
    }

    @Override
    public void restore() {
        bottomNavigation.restoreBottomNavigation();
    }

    public void showReviewDialogFragment(View view) {
        ReviewDialogFragment reviewDialogFragment = new ReviewDialogFragment();
        reviewDialogFragment.show(getFragmentManager(),"Review Dialog");
    }


    class loadMenu extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String jsonMenu = Utility.getJsonFromUrl("http://beaconref.16mb.com/demomenu.php");
            return jsonMenu;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("menu",s);
            findViewById(R.id.restaurant_loading).setVisibility(View.GONE);
            bean = new RestaurantBean(s);
            initToolbar();
            cart = new RestaurantCart();
        }
    }
}
