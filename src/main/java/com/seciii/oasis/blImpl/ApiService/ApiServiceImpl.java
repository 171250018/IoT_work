package com.seciii.oasis.blImpl.ApiService;

import com.seciii.oasis.bl.apiService.ApiService;
import com.seciii.oasis.bl.product.ProductService;
import com.seciii.oasis.data.ApiService.ApiMapper;
import com.seciii.oasis.data.Product.ProductMapper;
import com.seciii.oasis.po.Api;
import com.seciii.oasis.po.ApiSimple;
import com.seciii.oasis.po.Parameter;
import com.seciii.oasis.vo.ApiSimpleVO;
import com.seciii.oasis.vo.ApiVO;
import com.seciii.oasis.vo.ProductSimpleVO;
import com.seciii.oasis.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

    @Autowired
    private ApiMapper apiMapper;

    @Override
    public ResponseVO getApiList(int page){
        List<ApiSimple> apiSimpleList=apiMapper.getApiList((page-1)*10);
        List<ApiSimpleVO> res=new ArrayList<ApiSimpleVO>();
        for(ApiSimple a:apiSimpleList){
            res.add(ApiSimple2VO(a));
        }
        return ResponseVO.buildSuccess(res);
    }

    @Override
    public ResponseVO getApiDetail(int apiId){
        Api api=apiMapper.getApiById(apiId);
        if(api==null){
            return ResponseVO.buildFailure("该API不存在");
        }
        List<Parameter> requestList=apiMapper.getRequestList(apiId);
        List<Parameter> returnList=apiMapper.getReturnList(apiId);
        ApiVO res=new ApiVO();
        res.setApiInfo(api);
        res.setRequestList(requestList);
        res.setReturnList(returnList);
        return ResponseVO.buildSuccess(res);
    }

    private ApiSimpleVO ApiSimple2VO(ApiSimple apiSimple){
        ApiSimpleVO apiSimpleVO=new ApiSimpleVO();
        apiSimpleVO.setApiId(apiSimple.getApiId());
        apiSimpleVO.setName(apiSimple.getName());
        apiSimpleVO.setApiType(apiSimple.getApiType());
        apiSimpleVO.setReference(apiSimple.getReference());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        String time=apiSimple.getStartTime().format(formatter);
        apiSimpleVO.setStartTime(time);
        return apiSimpleVO;
    }
}
