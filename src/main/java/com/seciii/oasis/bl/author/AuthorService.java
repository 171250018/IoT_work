package com.seciii.oasis.bl.author;

import com.seciii.oasis.vo.ResponseVO;

public interface AuthorService {

    ResponseVO searchAuthorByName(String name);

    ResponseVO searchAuthorInfo(int aid);

    ResponseVO searchKeyword(int aid);

    ResponseVO searchCollaborate(int aid);

    ResponseVO searchConference(int aid);

    ResponseVO searchPaper(int aid);

}
