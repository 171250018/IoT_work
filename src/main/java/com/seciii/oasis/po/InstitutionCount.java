package com.seciii.oasis.po;

public class InstitutionCount {
    private int iid;
    private int count;

    public InstitutionCount(int iid, int count) {
        this.iid = iid;
        this.count = count;
    }

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
