package com.donkeymonkey.recyq.model;

public class Coupon {

    private String uid;
    private String couponName;
    private Integer couponValue;
    private Boolean isRedeemed;

    public Coupon() {
    }

    public Coupon(String uid, String couponName, Integer couponValue, Boolean isRedeemed) {
        this.uid = uid;
        this.couponName = couponName;
        this.couponValue = couponValue;
        this.isRedeemed = isRedeemed;
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
}
