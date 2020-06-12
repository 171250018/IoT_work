package com.seciii.oasis.bl.paper;

import com.seciii.oasis.vo.ResponseVO;

import java.util.Date;

public interface SearchService {

    ResponseVO searchPaper(int type, String content, int page);

    ResponseVO combineSearch(String title, String startyear, String endyear
                , String doi, String pubtitle, String authors, String keywords, String institutions
                , int page);

    ResponseVO searchPaperByPid(int pid);
}
