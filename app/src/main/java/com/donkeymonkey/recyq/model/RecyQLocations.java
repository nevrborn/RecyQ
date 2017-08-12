package com.donkeymonkey.recyq.model;

import java.util.ArrayList;

public class RecyQLocations {

    private static ArrayList<RecyQLocation> mRecyQLocations;

    public static ArrayList<RecyQLocation> getInstance() {
        if (mRecyQLocations == null) {
            ArrayList<RecyQLocation> recyQLocations = new ArrayList<RecyQLocation>();
            setInstance(recyQLocations);
        }

        return mRecyQLocations;
    }

    public static void setInstance(ArrayList<RecyQLocation> recyQLocations) {
        mRecyQLocations = recyQLocations;
    }

    public RecyQLocations(ArrayList<RecyQLocation> recyQLocations) {
        mRecyQLocations = recyQLocations;
    }

    public ArrayList<RecyQLocation> getRecyQLocations() {
        return mRecyQLocations;
    }

    public void setRecyQLocation(ArrayList<RecyQLocation> recyQLocations) {
        mRecyQLocations = recyQLocations;
    }

    public void addRecyQLocation(RecyQLocation recyQLocation) {
        mRecyQLocations.add(recyQLocation);
    }

}
