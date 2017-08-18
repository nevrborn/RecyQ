package com.donkeymonkey.recyq.model;

import java.util.ArrayList;
import java.util.List;

public class Partners {

    private static Partners mPartners;

    private String mPartnerName;
    private String mPartnerImage;
    private String mPartnerUrl;

    private List<Partners> mPartnerList = new ArrayList<>();

    public static Partners getInstance() {
        if (mPartners == null) {
            Partners partners = new Partners();
            setInstance(partners);
        }

        return mPartners;
    }

    private static void setInstance(Partners partners) {
        mPartners = partners;
    }


    public Partners() {
    }

    public Partners(String partnerName, String partnerImage, String partnerUrl) {
        mPartnerName = partnerName;
        mPartnerImage = partnerImage;
        mPartnerUrl = partnerUrl;
    }

    public String getPartnerName() {
        return mPartnerName;
    }

    public void setPartnerName(String partnerName) {
        mPartnerName = partnerName;
    }

    public String getPartnerImage() {
        return mPartnerImage;
    }

    public void setPartnerImage(String partnerImage) {
        mPartnerImage = partnerImage;
    }

    public String getPartnerUrl() {
        return mPartnerUrl;
    }

    public void setPartnerUrl(String partnerUrl) {
        mPartnerUrl = partnerUrl;
    }

    public List<Partners> getPartnerList() {

        mPartnerList.clear();
        addCurrentPartners();

        return mPartnerList;
    }

    public void setPartnerList(List<Partners> partnerList) {
        mPartnerList = partnerList;
    }

    public void addPartner(Partners partner) {
        mPartnerList.add(partner);
    }

    public void addCurrentPartners() {

        Partners recyQ = new Partners("RecyQ", "recyq_logo", "http://zerowasteamsterdam.nl");
        Partners startUpInResidence = new Partners("Startup in residence", "startup_in_residence", "http://www.startupinresidence.com/");
        Partners creativeAssociates = new Partners("Creative Associates", "creative_associates", "https://www.vsbfonds.nl");
        Partners goUpCycle = new Partners("Go-Upcycle!", "go_upcycle", "");
        Partners VSBfonds = new Partners("VSB fonds", "vsb_fonds", "https://www.vsbfonds.nl");
        Partners buurtWerkkamers = new Partners("Buurtwerkkamers", "buurtwerkkamercooperatie", "https://buurtwerkkamer.nl");
        Partners zuidoostTV = new Partners("Zuidoost TV", "zuidoost_tv", "https://www.zuidoost.tv");
        Partners fawaka = new Partners("Fawaka Nederland", "fawaka", "http://www.fawakanederland.nl");
        Partners amsterdasmLovesBikes = new Partners("Amsterdam loves bikes", "amsterdam_loves_bikes", "https://www.amsterdam.nl/parkeren-verkeer/fiets/fietsdepot/");

        mPartnerList.add(recyQ);
        mPartnerList.add(startUpInResidence);
        mPartnerList.add(creativeAssociates);
        mPartnerList.add(goUpCycle);
        mPartnerList.add(VSBfonds);
        mPartnerList.add(buurtWerkkamers);
        mPartnerList.add(zuidoostTV);
        mPartnerList.add(fawaka);
        mPartnerList.add(amsterdasmLovesBikes);
    }
}
