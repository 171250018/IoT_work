package com.seciii.oasis.vo;

import java.util.ArrayList;

public class PaperRefRank {
    private ArrayList<Integer> pidlist;
    private ArrayList<String> titlelist;
    private ArrayList<Integer> reflist;

    public ArrayList<Integer> getPidlist() {
        return pidlist;
    }

    public void setPidlist(ArrayList<Integer> pidlist) {
        this.pidlist = pidlist;
    }

    public ArrayList<String> getTitlelist() {
        return titlelist;
    }

    public void setTitlelist(ArrayList<String> titlelist) {
        this.titlelist = titlelist;
    }

    public ArrayList<Integer> getReflist() {
        return reflist;
    }

    public void setReflist(ArrayList<Integer> reflist) {
        this.reflist = reflist;
    }

    public PaperRefRank(ArrayList<Integer> pidlist, ArrayList<String> titlelist, ArrayList<Integer> reflist) {
        this.pidlist = pidlist;
        this.titlelist = titlelist;
        this.reflist = reflist;
    }
}
