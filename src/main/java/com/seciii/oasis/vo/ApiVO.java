package com.seciii.oasis.vo;

import com.seciii.oasis.po.Api;
import com.seciii.oasis.po.Parameter;

import java.util.List;

public class ApiVO {
    private Api apiInfo;
    private List<Parameter> requestList;
    private List<Parameter> returnList;

    public Api getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(Api apiInfo) {
        this.apiInfo = apiInfo;
    }

    public List<Parameter> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<Parameter> requestList) {
        this.requestList = requestList;
    }

    public List<Parameter> getReturnList() {
        return returnList;
    }

    public void setReturnList(List<Parameter> returnList) {
        this.returnList = returnList;
    }



}
