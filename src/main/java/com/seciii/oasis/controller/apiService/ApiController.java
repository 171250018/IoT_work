package com.seciii.oasis.controller.apiService;

import com.seciii.oasis.bl.apiService.ApiService;
import com.seciii.oasis.bl.product.ProductService;
import com.seciii.oasis.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @RequestMapping(value = "/apiList")
    public ResponseVO getApiList(@RequestParam("page") int page){
        return apiService.getApiList(page);
    }

    @RequestMapping(value = "/apiDetail")
    public ResponseVO getApiDetail(@RequestParam("apiId") int apiId){
        return apiService.getApiDetail(apiId);
    }

}
