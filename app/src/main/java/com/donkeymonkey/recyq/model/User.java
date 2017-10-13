package com.donkeymonkey.recyq.model;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.List;

public class User{

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
    private Boolean didReceiveRecyQBags;

    private Double amountOfPlastic;
    private Double amountOfPaper;
    private Double amountOfTextile;
    private Double amountOfEWaste;
    private Double amountOfBioWaste;
    private Double amountOfGlass;

    private String registeredVia;
    private String dateCreated;
    private Boolean completed;
    private String uid;
    private int spentCoins;

    //private List<WasteDepositInfo> wasteDepositInfo;

    private boolean userIsLoggedIn;
    private int tokens;
    private int treesSaved;

    private Uri mPhotoPath;

    private boolean isLoggingOut;

    private User() {

    }

    // constructor
    private User(FirebaseUser firebaseUser) {
        name = firebaseUser.getDisplayName();
        addedByUser = firebaseUser.getEmail();
        key = firebaseUser.getUid();
        setUid(key);
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

    public void clearUser() {
        mUser = null;
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

    public void setIsLoggedIn(boolean isLoggedIn) {
        userIsLoggedIn = isLoggedIn;
    }

    public boolean isUserIsLoggedIn() {
        return userIsLoggedIn;
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
        if (amountOfPlastic == null) {
            return 0.0;
        }
        return amountOfPlastic;

    }

    public void setAmountOfPlastic(Double amountOfPlastic) {

        if (amountOfPlastic == null) {
            amountOfPlastic = 0.0;
        }

        this.amountOfPlastic = amountOfPlastic;
    }

    
    public Double getAmountOfPaper() {
        if (amountOfPaper == null) {
                return 0.0;
        }
        return amountOfPaper;
    }

    public void setAmountOfPaper(Double amountOfPaper) {

        if (amountOfPaper == null) {
            amountOfPaper = 0.0;
        }

        this.amountOfPaper = amountOfPaper;
    }

    
    public Double getAmountOfTextile() {
        if (amountOfTextile == null) {
            return 0.0;
        }
        return amountOfTextile;
    }

    public void setAmountOfTextile(Double amountOfTextile) {

        if (amountOfTextile == null) {
            amountOfTextile = 0.0;
        }

        this.amountOfTextile = amountOfTextile;
    }

    
    public Double getAmountOfEWaste() {
        if (amountOfEWaste == null) {
            return 0.0;
        }
        return amountOfEWaste;
    }

    public void setAmountOfEWaste(Double amountOfEWaste) {

        if (amountOfEWaste == null) {
            amountOfEWaste = 0.0;
        }

        this.amountOfEWaste = amountOfEWaste;
    }

    
    public Double getAmountOfBioWaste() {
        if (amountOfBioWaste == null) {
            return 0.0;
        }
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

    
    public Double getAmountOfGlass() {
        if (amountOfGlass == null) {
            return 0.0;
        }
        return amountOfGlass;
    }

    public void setAmountOfGlass(Double amountOfGlass) {
        this.amountOfGlass = amountOfGlass;
    }

//    public List<WasteDepositInfo> getWasteDepositInfo() {
//        return wasteDepositInfo;
//    }
//
//    public void setWasteDepositInfo(List<WasteDepositInfo> wasteDepositInfo) {
//        this.wasteDepositInfo = wasteDepositInfo;
//    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Double getTotalKgRecycled() {
        if (mUser.isUserIsLoggedIn()) {
            return getAmountOfTextile() + getAmountOfPaper() + getAmountOfPlastic() + getAmountOfGlass() + getAmountOfBioWaste() + getAmountOfEWaste();
        }

        return 0.0;
    }

    public Double getTotalCo2Saved() {
        if (mUser.isUserIsLoggedIn()) {
            return getTextileC02Saved() + getPaperC02Saved() + getPlasticsC02Saved() + getGlassC02Saved() + getBioWasteC02Saved() + getEWasteC02Saved();
        }

        return 0.0;
    }

    public Double getTextileC02Saved() {
        if (getAmountOfTextile() != null) {
            return Math.ceil((mUser.getAmountOfTextile()/35) * 50);
        }

        return 0.0;
    }

    public Double getPaperC02Saved() {
        if (getAmountOfPaper() != null) {
            return Math.ceil((mUser.getAmountOfPaper()/35) * 50);
        }

        return 0.0;
    }

    public Double getGlassC02Saved() {
        if (getAmountOfGlass() != null) {
            return Math.ceil((mUser.getAmountOfGlass()/35) * 50);
        }

        return 0.0;
    }

    public Double getPlasticsC02Saved() {
        if (getAmountOfPlastic() != null) {
            return Math.ceil((mUser.getAmountOfPaper()/35) * 50);
        }

        return 0.0;
    }

    public Double getBioWasteC02Saved() {
        if (getAmountOfBioWaste() != null) {
            return Math.ceil((mUser.getAmountOfBioWaste()/35) * 50);
        }

        return 0.0;
    }

    public Double getEWasteC02Saved() {
        if (getAmountOfEWaste() != null) {
            return Math.ceil((mUser.getAmountOfEWaste()/35) * 50);
        }

        return 0.0;
    }

    public int getTokens() {
        int tokens = 0;

        if (getTotalKgRecycled() != null && getSpentCoins() != null) {
            tokens = (int) Math.round(getTotalKgRecycled() / 35);
            tokens = tokens - getSpentCoins();
            return tokens;
        }

        return 0;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public int getTreesSaved() {
        if (getTotalCo2Saved() != null) {
            return (int) Math.round(getTotalCo2Saved() / 16.6);
        }

        return 0;
    }

    public float getRemainingCO2ToSaveOneTree() {
        if (getTotalCo2Saved() != 0) {
            return (float) (100 / (getTotalCo2Saved() % 16.6));
        } else {
            return 0;
        }

    }

    public float getRemainingKGToEarnOneToken() {
        if (getTokens() != 0) {
            return (float) (100 / (getTokens() % 35));
        } else {
            return 0;
        }

    }

    public Uri getPhotoPath() {
        return mPhotoPath;
    }

    public void setPhotoPath(Uri photoPath) {
        mPhotoPath = photoPath;
    }

    public boolean isLoggingOut() {
        return isLoggingOut;
    }

    public void setLoggingOut(boolean loggingOut) {
        isLoggingOut = loggingOut;
    }

    public Boolean getDidReceiveRecyQBags() {
        return didReceiveRecyQBags;
    }

    public void setDidReceiveRecyQBags(Boolean didReceiveRecyQBags) {
        this.didReceiveRecyQBags = didReceiveRecyQBags;
    }

    public String getRegisteredVia() {
        return registeredVia;
    }

    public void setRegisteredVia(String registeredVia) {
        this.registeredVia = registeredVia;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

//    public String getFullName() {
//        String firstName = "";
//        String lastName = "";
//
//        if (getName() != null) firstName = getName().toString();
//        if (getLastName() != null) lastName = getLastName().toString();
//
//        if (firstName.length() != 0) firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
//        if (lastName.length() != 0) lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
//
//        return firstName + " " + lastName;
//    }
}
