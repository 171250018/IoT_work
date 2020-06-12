package com.seciii.oasis.vo;

import java.util.ArrayList;

public class ResultList {
    private ArrayList<PaperVO> resultlist;

    public ArrayList<PaperVO> getResultlist() {
        return resultlist;
    }

    public void setResultlist(ArrayList<PaperVO> resultlist) {
        this.resultlist = resultlist;
    }

    public ResultList(ArrayList<PaperVO> resultlist) {
        this.resultlist = resultlist;
    }
}
