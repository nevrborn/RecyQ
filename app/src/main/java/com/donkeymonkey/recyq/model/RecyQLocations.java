package com.donkeymonkey.recyq.model;

import java.util.ArrayList;
import java.util.List;

public class RecyQLocations {

    private static RecyQLocations mRecyQLocations;
    private static List<RecyQLocation> mRecyQLocationsList = new ArrayList<>();

    public static RecyQLocations getInstance() {
        if (mRecyQLocations == null) {
            RecyQLocations recyQLocations = new RecyQLocations();
            setInstance(recyQLocations);
        }

        return mRecyQLocations;
    }

    public RecyQLocations() {

    }

    public static void setInstance(RecyQLocations recyQLocations) {
        mRecyQLocations = recyQLocations;
    }

    public RecyQLocations(RecyQLocations recyQLocations) {
        mRecyQLocations = recyQLocations;
    }

    public RecyQLocations getRecyQLocations() {
        return mRecyQLocations;
    }

    public void setRecyQLocation(RecyQLocations recyQLocations) {
        mRecyQLocations = recyQLocations;
    }

    public void addRecyQLocation(RecyQLocation recyQLocation) {
        mRecyQLocationsList.add(recyQLocation);
    }

    public List<RecyQLocation> getRecyQLocationsList() {
        return mRecyQLocationsList;
    }

    public static void setRecyQLocationsList(List<RecyQLocation> mRecyQLocationsList) {
        RecyQLocations.mRecyQLocationsList = mRecyQLocationsList;
    }
}
