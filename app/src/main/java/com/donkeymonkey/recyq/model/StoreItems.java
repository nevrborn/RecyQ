package com.donkeymonkey.recyq.model;

import java.util.ArrayList;

public class StoreItems {

    private static ArrayList<StoreItem> mStores;

    public static ArrayList<StoreItem> getInstance() {
        if (mStores == null) {
            ArrayList<StoreItem> stores = new ArrayList<StoreItem>();
            setInstance(stores);
        }

        return mStores;
    }

    public static void setInstance(ArrayList<StoreItem> stores) {
        mStores = stores;
    }

    public StoreItems(ArrayList<StoreItem> stores) {
        mStores = stores;
    }

    public ArrayList<StoreItem> getStores() {
        return mStores;
    }

    public void setStores(ArrayList<StoreItem> stores) {
        mStores = stores;
    }

    public void addStore(StoreItem store) {
        mStores.add(store);
    }
}
