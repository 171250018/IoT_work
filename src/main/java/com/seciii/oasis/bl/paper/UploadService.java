package com.seciii.oasis.bl.paper;

import com.seciii.oasis.vo.ResponseVO;

public interface UploadService {

    ResponseVO uploadCsv(String filePath);
}
