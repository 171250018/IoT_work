package com.seciii.oasis.bl.conference;

import com.seciii.oasis.vo.ResponseVO;

public interface ConferenceService {

    ResponseVO searchConferenceByName(String name);

    ResponseVO searchConferenceInfo(int cid);

    ResponseVO searchAuthorList(int cid);

    ResponseVO searchInstitutionList(int cid);

    ResponseVO searchKeyword(int cid);

    ResponseVO searchPaper(int cid);

}
