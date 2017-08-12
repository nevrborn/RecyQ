package com.donkeymonkey.recyq.model;

import com.donkeymonkey.recyq.defines.Defines;

public class RecyQLocation {

    private String district;
    private String name;
    private String address;
    private String postalCodePlace;
    private String phoneNumber;
    private Double latitude;
    private Double longitude;
    private String email;
    private String websiteURL;

    public RecyQLocation() {
    }

    public RecyQLocation(String district, String name, String address, String postalCodePlace, String phonenumber, Double latitude, Double longitude, String email, String websiteURL) {
        this.district = district;
        this.name = name;
        this.address = address;
        this.postalCodePlace = postalCodePlace;
        this.phoneNumber = phonenumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.email = email;
        this.websiteURL = websiteURL;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCodePlace() {
        return postalCodePlace;
    }

    public void setPostalCodePlace(String postalCodePlace) {
        this.postalCodePlace = postalCodePlace;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phonenumber) {
        this.phoneNumber = phonenumber;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }
}
