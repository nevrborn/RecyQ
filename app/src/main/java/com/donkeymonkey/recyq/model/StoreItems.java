package com.donkeymonkey.recyq.model;

import java.util.ArrayList;

public class StoreItems {

    private static StoreItems mStoresItems;
    private ArrayList<StoreItem> mStoreItemArrayList = new ArrayList<>();

    public static StoreItems getInstance() {
        if (mStoresItems == null) {
            StoreItems stores = new StoreItems();
            setInstance(stores);
        }

        return mStoresItems;
    }

    private static void setInstance(StoreItems stores) {
        mStoresItems = stores;
    }

    public ArrayList<StoreItem> getStoresItems() {
        return mStoreItemArrayList;
    }

    public void setStores(ArrayList<StoreItem> stores) {
        mStoreItemArrayList = stores;
    }

    public void addStoreItem(StoreItem storeItem) {
        mStoreItemArrayList.add(storeItem);
    }

    public void clear() {
        mStoreItemArrayList.clear();
    }

    public StoreItem findStoreItemWithKey(String key) {

        StoreItem storeitem = new StoreItem();

        for (StoreItem item: mStoreItemArrayList) {
            if (item.getKey().equals(key)) {
                storeitem = item;
            }
        }
        return storeitem;
    }

}
