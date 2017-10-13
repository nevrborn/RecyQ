package com.donkeymonkey.recyq.model;

public class WasteDepositInfo {

    private String Admin;
    private String Datum;
    private String Locatie;
    private Integer Bio;
    private Integer EWaste;
    private Integer Papier;
    private Integer Plastic;
    private Integer Textiel;

    public WasteDepositInfo() {
    }

    public WasteDepositInfo(String admin, String datum, String locatie, Integer bio, Integer EWaste, Integer papier, Integer plastic, Integer textiel) {
        Admin = admin;
        Datum = datum;
        Locatie = locatie;
        Bio = bio;
        this.EWaste = EWaste;
        Papier = papier;
        Plastic = plastic;
        Textiel = textiel;
    }

    public String getAdmin() {
        return Admin;
    }

    public void setAdmin(String admin) {
        Admin = admin;
    }

    public String getDatum() {
        return Datum;
    }

    public void setDatum(String datum) {
        Datum = datum;
    }

    public String getLocatie() {
        return Locatie;
    }

    public void setLocatie(String locatie) {
        Locatie = locatie;
    }

    public Integer getBio() {
        return Bio;
    }

    public void setBio(Integer bio) {
        Bio = bio;
    }

    public Integer getEWaste() {
        return EWaste;
    }

    public void setEWaste(Integer EWaste) {
        this.EWaste = EWaste;
    }

    public Integer getPapier() {
        return Papier;
    }

    public void setPapier(Integer papier) {
        Papier = papier;
    }

    public Integer getPlastic() {
        return Plastic;
    }

    public void setPlastic(Integer plastic) {
        Plastic = plastic;
    }

    public Integer getTextiel() {
        return Textiel;
    }

    public void setTextiel(Integer textiel) {
        Textiel = textiel;
    }
}
