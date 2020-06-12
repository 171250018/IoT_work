package com.seciii.oasis.data.paper;

import com.seciii.oasis.po.Author;
import com.seciii.oasis.po.Paper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface PaperMapper {

    int insertPaper(Paper paper);

    int selectByIidPid(@Param("iid") int iid, @Param("pid") int pid);

    Paper selectPaperByPid(@Param("pid") int pid);

    Paper selectPaperByDoi(@Param("doi") String doi);

    List<Integer> selectPaperPidByAid(@Param("aid") int aid, @Param("beginnum") int begin, @Param("endnum") int end);

    List<Integer> selectPaperPidByIid(@Param("iid") int iid, @Param("beginnum") int begin, @Param("endnum") int end);

    List<Integer> selectPaperPidByKid(@Param("kid") int kid, @Param("beginnum") int begin, @Param("endnum") int end);

    List<Paper> selectPaperByTitle(@Param("doctitle") String doctitle, @Param("beginnum") int begin, @Param("endnum") int end);

    List<Paper> selectPaperByYear(@Param("startyear")Date startyear, @Param("endyear") Date endyear, @Param("beginnum") int begin, @Param("endnum") int end);
	
	List<Paper> selectPaperByPubTitle(@Param("pubtitle")String pubtitle, @Param("beginnum") int begin, @Param("endnum") int end);

	List<Paper> combineSelect(@Param("doctitle") String doctitle,
                              @Param("startyear") String startyear,
                              @Param("endyear") String endyear,
                              @Param("doi") String doi,
                              @Param("pubtitle") String pubtitle,
                              @Param("authorList") List<String> authorList,
                              @Param("keywordList") List<String> keywordList,
                              @Param("institutionList") List<String> institutionList,
                              @Param("beginnum") int begin,
                              @Param("endnum") int end);



}
