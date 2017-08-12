package com.donkeymonkey.recyq.model;

import java.util.ArrayList;

public class Coupons {

    private static ArrayList<Coupon> mCoupons;

    public static ArrayList<Coupon> getInstance() {
        if (mCoupons == null) {
            ArrayList<Coupon> Coupons = new ArrayList<Coupon>();
            setInstance(Coupons);
        }

        return mCoupons;
    }

    public static void setInstance(ArrayList<Coupon> Coupons) {
        mCoupons = Coupons;
    }

    public Coupons(ArrayList<Coupon> Coupons) {
        mCoupons = Coupons;
    }

    public ArrayList<Coupon> getCoupons() {
        return mCoupons;
    }

    public void setCoupon(ArrayList<Coupon> Coupons) {
        mCoupons = Coupons;
    }

    public void addCoupon(Coupon Coupon) {
        mCoupons.add(Coupon);
    }
    
}
