package com.seciii.oasis.bl.institution;

import com.seciii.oasis.vo.ResponseVO;

public interface InstitutionService {

    ResponseVO searchInstitutionByName(String name);

    ResponseVO searchInstitutionInfo(int iid);

    ResponseVO searchKeyword(int iid);

    ResponseVO searchAuthorList(int iid);

    ResponseVO searchCollaborate(int iid);

    ResponseVO searchPaper(int iid);
}
