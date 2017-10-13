package com.donkeymonkey.recyq.model;

public class Coupon {

    private String uid;
    private String couponName;
    private Integer couponValue;
    private Boolean isRedeemed;
    private String shopName;
    private String ownerID;
    private String dateCreated;
    private Integer validationCode;

    public Coupon() {
    }

    public Coupon(String uid, String couponName, Integer couponValue, Boolean isRedeemed, String shopName, String ownerID, String dateCreated, Integer validationCode) {
        this.uid = uid;
        this.couponName = couponName;
        this.couponValue = couponValue;
        this.isRedeemed = isRedeemed;
        this.shopName = shopName;
        this.ownerID = ownerID;
        this.dateCreated = dateCreated;
        this.validationCode = validationCode;
    }

    public String getUid() {
        return uid;
    }

    public String getCouponName() {
        return couponName;
    }

    public Integer getCouponValue() {
        return couponValue;
    }

    public Boolean getRedeemed() {
        return isRedeemed;
    }

    public void setRedeemed(Boolean redeemed) {
        isRedeemed = redeemed;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public void setCouponValue(Integer couponValue) {
        this.couponValue = couponValue;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(Integer validationCode) {
        this.validationCode = validationCode;
    }
}
