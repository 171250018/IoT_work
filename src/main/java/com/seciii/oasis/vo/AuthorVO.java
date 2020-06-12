package com.seciii.oasis.vo;

import com.seciii.oasis.po.Author;

import java.util.ArrayList;
import java.util.List;

public class AuthorVO {
    private int aid;
    private String aname;
    private ArrayList<String> institutions;
    private List<Integer> iidList;
    private int paperCount;
    private int paperCited;
    private double activity;

    public List<Integer> getIidList() {
        return iidList;
    }

    public void setIidList(List<Integer> iidList) {
        this.iidList = iidList;
    }

    public int getPaperCited() {
        return paperCited;
    }

    public void setPaperCited(int paperCited) {
        this.paperCited = paperCited;
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

    public int getPaperCount() {
        return paperCount;
    }

    public void setPaperCount(int paperCount) {
        this.paperCount = paperCount;
    }

    public AuthorVO(Author author){
        this.aid = author.getAid();
        this.aname = author.getAname();
        this.paperCount = author.getPaperamount();
        this.paperCited = author.getPapercited();
        this.activity = author.getActivity();
    }
}
