package com.seciii.oasis.data.author;

import com.seciii.oasis.po.Author;
import com.seciii.oasis.po.AuthorCol;
import com.seciii.oasis.po.AuthorPaperDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthorDetailMapper {

    List<AuthorCol> searchCollaboration(@Param("aid")int aid);

    List<Integer> searchInstitutionByAid(@Param("aid")int aid);

    List<Integer> searchKeywordByAid(@Param("aid")int aid);

    List<Integer> searchConferenceByAid(@Param("aid")int aid);

    List<Integer> searchPaperByAid(@Param("aid")int aid);

    List<AuthorPaperDetail> searchAuthorPaperDetail(@Param("aid")int aid);

}
