package com.example.harmeet.thetrail2;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by Harmeet on 25-08-2016.
 */
public class RestaurantCart {
    ArrayList<Product> cartItems;
    CartCustomization cartCustomization;
    int numberOfItems;
    int total;

    public RestaurantCart() {
        cartItems = new ArrayList<Product>();
        cartCustomization = new CartCustomization();
        total=0;
        numberOfItems=0;
    }

    public void addProduct(int currentProductId, HashMap<Integer,HashSet<Integer>> customization){

        //for(Product cartProduct:cartItems)
          //  Log.d("bcart",cartProduct.optionsId.toString());

        HashSet<Integer> optionList = new HashSet<Integer>();
        for(Integer key:customization.keySet()){
            optionList.addAll(customization.get(key));
        }

        //Log.d("allkeys",optionList.toString());

        Product product = new Product(currentProductId, optionList);

        boolean newItem = true;

        /*for(Product cartProduct:cartItems)
        Log.d("cart",cartProduct.optionsId.toString());*/

        for(Product cartProduct:cartItems){
            //Log.d("compare",cartProduct.optionsId.toString()+"------"+product.optionsId.toString());
            if(product.productId == cartProduct.productId && product.optionsId.equals(cartProduct.optionsId)){
                //Log.d("new item check",product.optionsId.equals(cartProduct.optionsId)+"");
                cartProduct.quantity++;
                newItem = false;
                Log.d("new item","false");
            }
        }
        if(newItem){
            cartItems.add(product);
            Log.d("new item","true");
        }


    }

    public void removeProduct(int productId, HashSet<Integer> optionsId){


        for(int i=0;i<cartItems.size();i++){
            if(cartItems.get(i).productId == productId && cartItems.get(i).optionsId.equals(optionsId)){
                if(cartItems.get(i).quantity>1)
                    cartItems.get(i).quantity--;
                else
                    cartItems.remove(i);
            }
        }
    }


    class Product{
        int productId,quantity;
        //HashMap<Integer,HashSet<Integer>> customization;
        HashSet<Integer> optionsId;

        public Product(int productId, HashSet<Integer> optionsId) {
            this.optionsId = optionsId;
            //this.customization = customization;
            this.productId = productId;
            this.quantity=1;
        }
        public Product(int productId, HashSet<Integer> optionsId,int quantity) {
            //this.optionsId = optionsId;
            this.productId = productId;
            this.quantity=quantity;
        }
    }


    class CartCustomization{
        int customizationId;
        ArrayList<Integer> options;

        public CartCustomization() {
            options = new ArrayList<Integer>();
        }
    }
}
