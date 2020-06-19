package com.seciii.oasis.vo;

import java.util.List;

public class AllDataVO {
    private int did;
    private int pid;
    private String pname;
    private List<AttrDataVO> attrDataVOList;

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

    public List<AttrDataVO> getAttrDataVOList() {
        return attrDataVOList;
    }

    public void setAttrDataVOList(List<AttrDataVO> attrDataVOList) {
        this.attrDataVOList = attrDataVOList;
    }



}
