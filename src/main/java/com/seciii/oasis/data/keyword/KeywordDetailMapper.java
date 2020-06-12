package com.seciii.oasis.data.keyword;

import com.seciii.oasis.po.Keyword;
import com.seciii.oasis.po.KeywordCol;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KeywordDetailMapper {

    List<Keyword> searchRelatedKeyword(@Param("kid")int kid);

    List<Integer> selectAuthorByKid(@Param("kid") int kid);

    List<Integer> selectInstitutionByKid(@Param("kid")int kid);

    List<Integer> selectPaperByKid(@Param("kid")int kid);

    List<KeywordCol> searchCollaboration(@Param("kid")int kid);

}
