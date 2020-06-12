package com.seciii.oasis.data.conference;

import com.seciii.oasis.po.Conference;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ConferenceDetailMapper {

    Conference selectConferenceByName(@Param("cname") String cname);

    List<Conference> selectConferenceByAlikeName(@Param("cname")String cname);

    void insertConference(Conference conference);

    void insertCidPid(@Param("cid")int cid, @Param("pid")int pid);

    Conference selectConferenceByCid(@Param("cid")int cid);

    void updateConference(Conference conference);

    List<Integer> searchPaperByCid(@Param("cid")int cid);

    List<Integer> searchAuthorByCid(@Param("cid")int cid);

    List<Integer> searchInstitutionByCid(@Param("cid")int cid);

    List<Integer> searchKeywordByCid(@Param("cid")int cid);

    int selectCidByPid(@Param("pid")int pid);

}
