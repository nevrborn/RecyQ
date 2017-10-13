package com.donkeymonkey.recyq.model;

import java.util.ArrayList;

public class Community {

    private static Community mCommunity;

    private int mCommunitySize = 0;
    private Double mTotalKilosDelivered = 0.0;
    private Double mTotalCO2Saved = 0.0;
    private int mTotalTokensEarned = 0;
    private int mTotalTreesSaved = 0;

    public static Community getInstance() {
        if (mCommunity == null) {
            Community community = new Community();
            setInstance(community);
        }

        return mCommunity;
    }

    public static void setInstance(Community community) {
        mCommunity = community;
    }

    public int getCommunitySize() {
        return mCommunitySize;
    }

    public void setCommunitySize(int communitySize) {
        mCommunitySize = communitySize;
    }

    public Double getTotalKilosDelivered() {
        return mTotalKilosDelivered;
    }

    public Double getTotalCO2Saved() {
        return mTotalCO2Saved;
    }

    public int getTotalTokensEarned() {
        return mTotalTokensEarned;
    }

    public int getTotalTreesSaved() {
        return mTotalTreesSaved;
    }

    public void addKilos(Double value) {
        if (value != null) {
            mTotalKilosDelivered = mTotalKilosDelivered + value;
        }
    }

    public void addCo2(Double value) {
        if (value != 0) {
            mTotalCO2Saved = mTotalCO2Saved + value;
        }
    }

    public void addTokens(int value) {
        if (value != 0) {
            mTotalTokensEarned = mTotalTokensEarned + value;
        }
    }

    public void addTrees(int value) {
        if (value != 0) {
            mTotalTreesSaved = mTotalTreesSaved + value;
        }
    }

    public void clear() {
        mCommunitySize = 0;
        mTotalKilosDelivered = 0.0;
        mTotalCO2Saved = 0.0;
        mTotalTokensEarned = 0;
        mTotalTreesSaved = 0;
    }
}
