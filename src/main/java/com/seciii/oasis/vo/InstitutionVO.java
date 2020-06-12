package com.seciii.oasis.vo;

import com.seciii.oasis.po.Institution;

public class InstitutionVO {
    private int iid;
    private String iname;
    private int paperCount;
    private double activity;
    private int papercited;

    public double getActivity() {
        return activity;
    }

    public void setActivity(double activity) {
        this.activity = activity;
    }

    public int getPapercited() {
        return papercited;
    }

    public void setPapercited(int papercited) {
        this.papercited = papercited;
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

    public int getPaperCount() {
        return paperCount;
    }

    public void setPaperCount(int paperCount) {
        this.paperCount = paperCount;
    }

    public InstitutionVO(Institution institution){
        this.iid = institution.getIid();
        this.iname = institution.getIname();
        this.paperCount = institution.getPaperamount();
        this.papercited = institution.getPapercited();
        this.activity = institution.getActivity();
    }
}
