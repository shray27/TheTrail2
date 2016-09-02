package com.example.harmeet.thetrail2;

import android.app.Application;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by Harmeet on 28-08-2016.
 */
public class App extends Application {
        public static String lastUID = null;
        public static HashMap<String,Integer> list = new HashMap<String, Integer>();
        public static TreeMap<String,Integer> tList = new TreeMap<String, Integer>();
        public static void addEddyStone(String EUID,int rssi){
            list.put(EUID,rssi);
        }
}
