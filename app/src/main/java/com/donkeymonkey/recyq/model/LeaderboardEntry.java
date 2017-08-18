package com.donkeymonkey.recyq.model;


public class LeaderboardEntry {

    private String mKey;
    private String mName;
    private Double mTotalKilos;

    public LeaderboardEntry(String key, String name, Double totalKilos) {
        mKey = key;
        mName = name;
        mTotalKilos = totalKilos;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Double getTotalKilos() {
        return mTotalKilos;
    }

    public void setTotalKilos(Double totalKilos) {
        mTotalKilos = totalKilos;
    }
}
