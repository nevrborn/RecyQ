package com.donkeymonkey.recyq.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class User {

    private static User mUser;

    private String key;
    private String name;
    private String lastName;
    private String address;
    private String zipCode;
    private String city;
    private String phoneNumber;
    private String addedByUser;
    private String nearestWasteLocation;

    private Double amountOfPlastic;
    private Double amountOfPaper;
    private Double amountOfTextile;
    private Double amountOfEWaste;
    private Double amountOfBioWaste;
    private Double amountOfGlass;

    //private HashMap<String, String> wasteDepositInfo;
    private Boolean completed;
    private String uid;
    private Integer spentCoins;


    public Double getAmountOfGlass() {
        return amountOfGlass;
    }

    public void setAmountOfGlass(Double amountOfGlass) {
        this.amountOfGlass = amountOfGlass;
    }

//    public HashMap<String, String> getWasteDepositInfo() {
//        return wasteDepositInfo;
//    }
//
//    public void setWasteDepositInfo(HashMap<String, String> wasteDepositInfo) {
//        this.wasteDepositInfo = wasteDepositInfo;
//    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }



    private User() {

    }

    // constructor
    private User(FirebaseUser firebaseUser) {
        name = firebaseUser.getDisplayName();
        addedByUser = firebaseUser.getEmail();
        key = firebaseUser.getUid();
        setUid(key);
        setIsLoggedIn(true);
    }

    public static User getInstance() {
        if (mUser == null) {
            User user = new User();
            User.setInstance(user);
        }

        return mUser;
    }

    public static void setInstance(User user) {
        mUser = user;
    }

    public static User get() {
        return mUser;
    }

    public static void set() {
        // if we have a loggedin user, set mUser
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            mUser = new User(firebaseUser);
        }
    }

    public static void signOut() {
        FirebaseAuth.getInstance().signOut();
        mUser = null;
    }

    private void setIsLoggedIn(boolean isLoggedIn) {
        boolean sIsLoggedIn = isLoggedIn;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddedByUser() {
        return addedByUser;
    }

    public void setAddedByUser(String addedByUser) {
        this.addedByUser = addedByUser;
    }

    public String getNearestWasteLocation() {
        return nearestWasteLocation;
    }

    public void setNearestWasteLocation(String nearestWasteLocation) {
        this.nearestWasteLocation = nearestWasteLocation;
    }

    public Double getAmountOfPlastic() {
        return amountOfPlastic;
    }

    public void setAmountOfPlastic(Double amountOfPlastic) {

        if (amountOfPlastic == null) {
            amountOfPlastic = 0.0;
        }

        this.amountOfPlastic = amountOfPlastic;
    }

    public Double getAmountOfPaper() {
        return amountOfPaper;
    }

    public void setAmountOfPaper(Double amountOfPaper) {

        if (amountOfPaper == null) {
            amountOfPaper = 0.0;
        }

        this.amountOfPaper = amountOfPaper;
    }

    public Double getAmountOfTextile() {
        return amountOfTextile;
    }

    public void setAmountOfTextile(Double amountOfTextile) {

        if (amountOfTextile == null) {
            amountOfTextile = 0.0;
        }

        this.amountOfTextile = amountOfTextile;
    }

    public Double getAmountOfEWaste() {
        return amountOfEWaste;
    }

    public void setAmountOfEWaste(Double amountOfEWaste) {

        if (amountOfEWaste == null) {
            amountOfEWaste = 0.0;
        }

        this.amountOfEWaste = amountOfEWaste;
    }

    public Double getAmountOfBioWaste() {
        return amountOfBioWaste;
    }

    public void setAmountOfBioWaste(Double amountOfBioWaste) {

        if (amountOfBioWaste == null) {
            amountOfBioWaste = 0.0;
        }

        this.amountOfBioWaste = amountOfBioWaste;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getSpentCoins() {
        return spentCoins;
    }

    public void setSpentCoins(Integer spentCoins) {
        this.spentCoins = spentCoins;
    }
}
