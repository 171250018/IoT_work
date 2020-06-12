package com.seciii.oasis.vo;

import com.seciii.oasis.po.Keyword;

public class KeywordVO {
    private int kid;
    private String kname;
    private int paperCount;
    private double activity;

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

    public int getPaperCount() {
        return paperCount;
    }

    public void setPaperCount(int paperCount) {
        this.paperCount = paperCount;
    }

    public KeywordVO(Keyword keyword){
        this.kid = keyword.getKid();
        this.kname = keyword.getKname();
        this.paperCount = keyword.getPaperamount();
        this.activity = keyword.getActivity();
    }
}
