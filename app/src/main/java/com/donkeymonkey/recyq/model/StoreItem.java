package com.donkeymonkey.recyq.model;

import android.media.Image;

public class StoreItem {

    private String storeItemName;
    private String storeItemDesciption;
    private Image storeItemImage;
    private Integer storeItemPrice;

    public StoreItem(String storeItemName, String storeItemDesciption, Image storeItemImage, Integer storeItemPrice) {
        this.storeItemName = storeItemName;
        this.storeItemDesciption = storeItemDesciption;
        this.storeItemImage = storeItemImage;
        this.storeItemPrice = storeItemPrice;
    }

    public String getStoreItemName() {
        return storeItemName;
    }

    public String getStoreItemDesciption() {
        return storeItemDesciption;
    }

    public Image getStoreItemImage() {
        return storeItemImage;
    }

    public Integer getStoreItemPrice() {
        return storeItemPrice;
    }
}
