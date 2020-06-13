package com.seciii.oasis.vo;

import com.seciii.oasis.po.Author;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataSourceVO {
    private int did;
    private int pid;
    private String pname;
    private int month;
    private String startTime;
    private int status;

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    /*
    public DataSourceVO(Author author){
        this.aid = author.getAid();
        this.aname = author.getAname();
        this.paperCount = author.getPaperamount();
        this.paperCited = author.getPapercited();
        this.activity = author.getActivity();
    }

     */
}
