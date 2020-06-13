package com.seciii.oasis.po;

import java.time.LocalDateTime;

public class Product {

    private int pid;
    private String pname;
    private int nodeType;
    private int connectMethod;
    private int dataFormat;
    private int verificationMethod;
    private String productDescription;

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

    public int getNodeType() {
        return nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public int getConnectMethod() {
        return connectMethod;
    }

    public void setConnectMethod(int connectMethod) {
        this.connectMethod = connectMethod;
    }

    public int getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(int dataFormat) {
        this.dataFormat = dataFormat;
    }

    public int getVerificationMethod() {
        return verificationMethod;
    }

    public void setVerificationMethod(int verificationMethod) {
        this.verificationMethod = verificationMethod;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

}
