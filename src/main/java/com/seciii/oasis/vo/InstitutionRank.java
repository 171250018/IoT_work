package com.seciii.oasis.vo;

import com.seciii.oasis.po.Institution;

import java.util.ArrayList;

public class InstitutionRank {
    private ArrayList<Institution> institutionList;
    private ArrayList<Integer> countList;

    public InstitutionRank(ArrayList<Institution> institutionList, ArrayList<Integer> countList) {
        this.institutionList = institutionList;
        this.countList = countList;
    }

    public ArrayList<Institution> getInstitutionList() {
        return institutionList;
    }

    public void setInstitutionList(ArrayList<Institution> institutionList) {
        this.institutionList = institutionList;
    }

    public ArrayList<Integer> getCountList() {
        return countList;
    }

    public void setCountList(ArrayList<Integer> countList) {
        this.countList = countList;
    }
}
