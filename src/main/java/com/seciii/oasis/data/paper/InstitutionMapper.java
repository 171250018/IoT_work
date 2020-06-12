package com.seciii.oasis.data.paper;

import com.seciii.oasis.po.Institution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InstitutionMapper {

    void insertInstitution(Institution institution);

    void updateInstitution(Institution institution);

    void insertIidPid(@Param("iid")int iid, @Param("pid")int pid);

    Institution selectInstitutionByIid(@Param("iid") int iid);

    List<Institution> selectInstitutionByIname(@Param("iname") String iname);

    int selectIidByExactName(@Param("iname")String iname);

    Institution selectInstitutionByExactName(@Param("iname") String iname);

    List<Integer> selectIidByPid(@Param("pid")int pid);


}
