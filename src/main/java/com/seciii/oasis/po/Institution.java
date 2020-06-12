package com.seciii.oasis.po;

public class Institution {

    private int iid = 0;
    private String iname = "";
    private int paperamount;
    private double activity;
    private int papercited;

    public int getPaperamount() {
        return paperamount;
    }

    public void setPaperamount(int paperamount) {
        this.paperamount = paperamount;
    }

    public int getPapercited() {
        return papercited;
    }

    public void setPapercited(int papercited) {
        this.papercited = papercited;
    }

    public double getActivity() {
        return activity;
    }

    public void setActivity(double activity) {
        this.activity = activity;
    }

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public String getIname() {
        return iname;
    }

    public void setIname(String iname) {
        this.iname = iname;
    }
}
