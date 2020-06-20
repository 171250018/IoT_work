package com.seciii.oasis.po;

public class Parameter {

    private int dpid;
    private String name;
    private String dataType;
    private String example;
    private int isNess;
    private String description;

    public int getDpid() {
        return dpid;
    }

    public void setDpid(int dpid) {
        this.dpid = dpid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public int getIsNess() {
        return isNess;
    }

    public void setIsNess(int isNess) {
        this.isNess = isNess;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
