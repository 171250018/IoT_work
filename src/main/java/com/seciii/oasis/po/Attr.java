package com.seciii.oasis.po;

import com.seciii.oasis.vo.AttrDataVO;

import java.time.LocalDateTime;

public class Attr {

    private int aid;
    private int funcType;
    private String aname;
    private String identifier;
    private String dataType;
    private int rangeType;
    private int dataLen;
    private int lowerLimit;
    private int higherLimit;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getFuncType() {
        return funcType;
    }

    public void setFuncType(int funcType) {
        this.funcType = funcType;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getRangeType() {
        return rangeType;
    }

    public void setRangeType(int rangeType) {
        this.rangeType = rangeType;
    }

    public int getDataLen() {
        return dataLen;
    }

    public void setDataLen(int dataLen) {
        this.dataLen = dataLen;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(int lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public int getHigherLimit() {
        return higherLimit;
    }

    public void setHigherLimit(int higherLimit) {
        this.higherLimit = higherLimit;
    }

    public AttrDataVO getVO(){
        AttrDataVO attrDataVO=new AttrDataVO();
        attrDataVO.setAid(aid);
        attrDataVO.setFuncType(funcType);
        attrDataVO.setAname(aname);
        attrDataVO.setIdentifier(identifier);
        attrDataVO.setDataType(dataType);
        attrDataVO.setRangeType(rangeType);
        attrDataVO.setDataLen(dataLen);
        attrDataVO.setLowerLimit(lowerLimit);
        attrDataVO.setHigherLimit(higherLimit);
        return attrDataVO;
    }



}
