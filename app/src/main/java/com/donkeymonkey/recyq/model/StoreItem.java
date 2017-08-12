package com.donkeymonkey.recyq.model;

import java.util.ArrayList;

public class StoreItem {

    private String shopName;
    private String itemName;
    private String detailDescription;
    private int tokenAmount;
    private String imageString;

    public StoreItem() {

    }

    public StoreItem(String shopName, String itemName, String detailDescription, int tokenAmount, String imageString) {
        this.shopName = shopName;
        this.itemName = itemName;
        this.detailDescription = detailDescription;
        this.tokenAmount = tokenAmount;
        this.imageString = imageString;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDetailDescription() {
        return detailDescription;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    public int getTokenAmount() {
        return tokenAmount;
    }

    public void setTokenAmount(int tokenAmount) {
        this.tokenAmount = tokenAmount;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }
}
