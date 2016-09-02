package com.example.harmeet.thetrail2;

import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Harmeet on 24-08-2016.
 */
public class RestaurantBean {

    int storeId,menuId;
    String storeName, storeType;
    ArrayList<RestaurantTab> tabs;

    public int getMenuId() {
        return menuId;
    }

    public int getStoreId() {
        return storeId;
    }

    public String getStoreType() {
        return storeType;
    }

    public ArrayList<RestaurantTab> getTabs() {
        return tabs;
    }

    public RestaurantBean(String json) {
        tabs = new ArrayList<RestaurantTab>();
        try {
            JSONObject jRestaurant =  new JSONObject(json);
            storeId = jRestaurant.getInt("store_id");
            storeName = jRestaurant.getString("store_name");
            storeType = jRestaurant.getString("store_type");
            menuId = jRestaurant.getInt("menu_id");
            JSONArray jTabs = jRestaurant.getJSONArray("tabs");
            for(int i=0;i<jTabs.length();i++){
                tabs.add(new RestaurantTab(jTabs.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public class RestaurantTab {
        int tabId;
        String tabName;
        ArrayList<RestaurantDishCategory> categories;
        public RestaurantTab(JSONObject object) {
            categories = new ArrayList<RestaurantDishCategory>();
            try {
                tabId = object.getInt("tab_id");
                tabName = object.getString("tab_name");
                JSONArray jCategories = object.getJSONArray("categories");
                for(int i=0;i<jCategories.length();i++)
                    categories.add(new RestaurantDishCategory(jCategories.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public class RestaurantDishCategory {
        int categoryId;
        String categoryName;
        ArrayList<Product> products;

        public RestaurantDishCategory(JSONObject object) {
            products = new ArrayList<Product>();
            try {
                categoryId = object.getInt("category_id");
                categoryName = object.getString("category_name");
                JSONArray jProducts = object.getJSONArray("products");
                for(int i=0;i<jProducts.length();i++)
                    products.add(new Product(jProducts.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class Product implements Serializable {
        int productId, basePrice;
        String productName, productDescription,productImageLink;
        boolean isVeg, isCustomizable, isDependable;
        ArrayList<Customization> customizations;

        public Product(JSONObject object) {
            customizations = new ArrayList<Customization>();
            try {
                productId = object.getInt("product_id");
                productName = object.getString("product_name");
                productImageLink = object.getString("product_image_link");
                productDescription = object.getString("product_description");
                basePrice = object.getInt("base_price");
                isVeg = object.getBoolean("is_veg");
                isCustomizable = object.getBoolean("is_customizable");
                isDependable = object.getBoolean("is_dependable");
                JSONArray jCustomizations = object.getJSONArray("customization");
                for(int i=0;i<jCustomizations.length();i++)
                    customizations.add(new Customization(jCustomizations.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class Customization implements Serializable{
        String name,type;
        boolean required;
        int minSelection, customizationId;
        ArrayList<Option> options;
        public Customization(JSONObject object) {
            options = new ArrayList<Option>();
            try {
                customizationId = object.getInt("customization_id");
                name = object.getString("name");
                required = object.getBoolean("required");
                type = object.getString("type");
                minSelection = object.getInt("min_selection");
                JSONArray jOptions = object.getJSONArray("options");
                for(int i=0;i<jOptions.length();i++)
                    options.add(new Option(jOptions.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public class Option{

        int optionId,price;
        String optionName;
        boolean isChargable;
        public Option(JSONObject object) {
            try {
                optionId = object.getInt("option_id");
                optionName = object.getString("option_name");
                isChargable = object.getBoolean("is_chargeable");
                price = object.getInt("price");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
