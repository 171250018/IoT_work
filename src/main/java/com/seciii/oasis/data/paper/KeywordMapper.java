package com.seciii.oasis.data.paper;

import com.seciii.oasis.po.Keyword;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KeywordMapper {

    void insertKeyword(Keyword keyword);

    void updateKeyword(Keyword keyword);

    void insertKidPid(@Param("kid")int kid, @Param("pid")int pid);

    Keyword selectKeywordByKid(@Param("kid") int kid);

    List<Keyword> selectKeywordByKname(@Param("kname") String kname);

    Keyword selectKeywordPOByExactKname(@Param("kname") String kname);

    int selectKeywordByExactKname(@Param("kname") String kname);

    List<Integer> selectKidByPid(@Param("pid") int pid);

}
