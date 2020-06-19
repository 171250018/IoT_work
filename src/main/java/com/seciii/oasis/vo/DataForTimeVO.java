package com.seciii.oasis.vo;

import com.seciii.oasis.po.DataForAid;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class DataForTimeVO {
    private int did;
    private Timestamp time;
    private List<DataForAid> dataForAidList;

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public List<DataForAid> getDataForAidList() {
        return dataForAidList;
    }

    public void setDataForAidList(List<DataForAid> dataForAidList) {
        this.dataForAidList = dataForAidList;
    }

}
