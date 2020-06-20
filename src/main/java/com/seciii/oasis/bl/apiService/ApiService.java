package com.seciii.oasis.bl.apiService;

import com.seciii.oasis.vo.ResponseVO;

public interface ApiService {

    ResponseVO getApiList(int page);

    ResponseVO getApiDetail(int apiId);


}
