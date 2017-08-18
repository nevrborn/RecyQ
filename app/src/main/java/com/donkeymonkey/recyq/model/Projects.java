package com.donkeymonkey.recyq.model;

import java.util.ArrayList;
import java.util.List;

public class Projects {

    private static Projects mProjects;
    private List<Projects> mProjectsList = new ArrayList<>();

    private String mProjectName;
    private String mProjectImage;
    private String mProjectText;
    private String mProjectUrl;

    public static Projects getInstance() {
        if (mProjects == null) {
            Projects projects = new Projects();
            setInstance(projects);
        }

        return mProjects;
    }

    private static void setInstance(Projects projects) {
        mProjects = projects;
    }

    public Projects() {
    }

    public Projects(String projectName, String projectImage, String projectText, String projectUrl) {
        mProjectName = projectName;
        mProjectImage = projectImage;
        mProjectText = projectText;
        mProjectUrl = projectUrl;

    }

    public void addProject(Projects project) {
        mProjectsList.add(project);
    }

    public List<Projects> getProjectsList() {
        mProjectsList.clear();
        addProjects();

        return mProjectsList;
    }

    public void addProjects() {

        Projects wormenHotel = new Projects("Wormen Hotel", "wormenhotel", "Meld je aan voor een Wormenhotel-Buurtcompost in jouw buurt!", "");
        Projects recyQJeans = new Projects("RecyQ Denim Bags", "denimjeans", "Laat je oude spijkerbroek upcyclen tot hippe en duurzame denim tas!", "");
        Projects weesFiets = new Projects("Go-Upcycle!", "weesfiets", "Meld je aan voor een exclusieve Fietsdepot weesfiets. Volledig opgeknapt voor een scherpe prijs!", "");
        Projects plasticsFantastic = new Projects("Plastic Fantastic Workshop", "plasticfantastic", "Meld je aan voor de Plastic Fantastic  Workshop. Creatief met plastic!", "");

        addProject(wormenHotel);
        addProject(recyQJeans);
        addProject(weesFiets);
        addProject(plasticsFantastic);
    }

    public String getProjectName() {
        return mProjectName;
    }

    public String getProjectImage() {
        return mProjectImage;
    }

    public String getProjectText() {
        return mProjectText;
    }

    public String getProjectUrl() {
        return mProjectUrl;
    }
}
