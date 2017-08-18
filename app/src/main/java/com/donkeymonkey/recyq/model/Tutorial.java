package com.donkeymonkey.recyq.model;

import java.util.ArrayList;
import java.util.List;

public class Tutorial {

    private static Tutorial mTutorial;
    private List<Tutorial> mTutorialList = new ArrayList<>();

    private String mTitle;
    private String mText;
    private String mButtonText;

    public static Tutorial getInstance() {
        if (mTutorial == null) {
            Tutorial tutorial = new Tutorial();
            setInstance(tutorial);
        }

        return mTutorial;
    }

    private static void setInstance(Tutorial tutorial) {
        mTutorial = tutorial;
    }

    public Tutorial() {
    }

    public Tutorial(String title, String text, String buttonText) {
        mTitle = title;
        mText = text;
        mButtonText = buttonText;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getText() {
        return mText;
    }

    public String getButtonText() {
        return mButtonText;
    }

    public void addTutorial(Tutorial tutorial) {
        mTutorialList.add(tutorial);
    }

    public List<Tutorial> getTutorialList() {
        mTutorialList.clear();
        addTutorials();
        return mTutorialList;
    }

    public void addTutorials() {

        Tutorial step1 = new Tutorial("Welkom bij RecyQ", "Je account is geopend. Je kunt nu beginnen met het scheiden van plastic, textiel en papier", "Stap 1");
        Tutorial step2 = new Tutorial("Lever je volle tassen in", "Haal een zero waste tas op bij een van de locaties om je afval in te leveren", "Stap 2");
        Tutorial step3 = new Tutorial("Verzilver je gespaarde tokens", "Voor korting op producten en diensten in de Zero Waste Store en bij ondernemers in de Amsterdamse Poort", "Stap 3");
        Tutorial step4 = new Tutorial("Red het milieu", "Draag bij aan een betere wereld", "Klaar");

        addTutorial(step1);
        addTutorial(step2);
        addTutorial(step3);
        addTutorial(step4);
    }
}
