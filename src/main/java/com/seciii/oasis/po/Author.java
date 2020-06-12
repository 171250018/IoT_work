package com.seciii.oasis.po;

import java.util.ArrayList;

public class Author {

    private int aid = 0;
    private String aname = "";
    private ArrayList<String> institutions;
    private int paperamount;
    private int papercited;
    private double activity;

    public int getPapercited() {
        return papercited;
    }

    public void setPapercited(int papercited) {
        this.papercited = papercited;
    }

    public int getPaperamount() {

        return paperamount;
    }

    public void setPaperamount(int paperamount) {
        this.paperamount = paperamount;
    }

    public double getActivity() {
        return activity;
    }

    public void setActivity(double activity) {
        this.activity = activity;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public ArrayList<String> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(ArrayList<String> institutions) {
        this.institutions = institutions;
    }
}
