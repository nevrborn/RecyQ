package com.donkeymonkey.recyq.model;

import java.util.ArrayList;
import java.util.List;

public class Coupons {

    private static Coupons mCoupons;
    private List<Coupon> mCouponList = new ArrayList<>();

    public static Coupons getInstance() {
        if (mCoupons == null) {
            Coupons coupons = new Coupons();
            setInstance(coupons);
        }

        return mCoupons;
    }

    public Coupons() {
    }

    public static void setInstance(Coupons coupons) {
        mCoupons = coupons;
    }

    public Coupons(Coupons coupons) {
        mCoupons = coupons;
    }

    public Coupons getCoupons() {
        return mCoupons;
    }

    public void setCoupon(Coupons coupons) {
        mCoupons = coupons;
    }

    public void addCoupon(Coupon Coupon) {
        mCouponList.add(Coupon);
    }

    public List<Coupon> getCouponList() {
        return mCouponList;
    }

    public void setCouponList(List<Coupon> couponList) {
        mCouponList = couponList;
    }

    public List<Coupon> getCouponsForUser(String uid) {

        List<Coupon> couponList = new ArrayList<>();

        for (Coupon coupon: getCouponList()) {

            if (coupon.getOwnerID().equals(uid)) couponList.add(coupon);

        }

        return couponList;

    }
}
