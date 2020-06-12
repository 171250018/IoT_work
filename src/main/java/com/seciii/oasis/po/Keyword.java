package com.seciii.oasis.po;

public class Keyword {

    private int kid = 0;
    private String kname = "";
    private int paperamount;
    private double activity;

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

    public int getKid() {
        return kid;
    }

    public void setKid(int kid) {
        this.kid = kid;
    }

    public String getKname() {
        return kname;
    }

    public void setKname(String kname) {
        this.kname = kname;
    }
}
