package com.seciii.oasis.bl.keyword;

import com.seciii.oasis.vo.ResponseVO;

public interface KeywordService {

    ResponseVO searchKeywordInfo(int kid);

    ResponseVO searchRelation(int kid);

    ResponseVO searchPaper(int kid);

    ResponseVO searchAuthorList(int kid);

    ResponseVO searchInstitutionList(int kid);
}
