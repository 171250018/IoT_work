package com.seciii.oasis.vo;

import java.util.Date;
import java.util.List;

public class SearchDatasForm {
    private int dataId;
    private List<Integer> attrlist;
    private Date start;
    private Date end;

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public List<Integer> getAttrlist() {
        return attrlist;
    }

    public void setAttrlist(List<Integer> attrlist) {
        this.attrlist = attrlist;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }



}
