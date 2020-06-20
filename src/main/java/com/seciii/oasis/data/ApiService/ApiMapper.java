package com.seciii.oasis.data.ApiService;

import com.seciii.oasis.po.Api;
import com.seciii.oasis.po.ApiSimple;
import com.seciii.oasis.po.Parameter;
import com.seciii.oasis.po.Product;
import com.seciii.oasis.vo.ProductSimpleVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApiMapper {

    List<ApiSimple> getApiList(int begin);

    Api getApiById(int apiId);

    List<Parameter> getRequestList(int apiId);

    List<Parameter> getReturnList(int apiId);

}
