package com.seciii.oasis.data.institution;

import com.seciii.oasis.po.Institution;
import com.seciii.oasis.po.InstitutionCol;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InstitutionDetailMapper {

    List<InstitutionCol> searchCollaboration(@Param("iid")int iid);

    List<Integer> searchAuthorByIid(@Param("iid")int iid);

    List<Integer> searchKeywordByIid(@Param("iid")int iid);

    List<Integer> searchPaperByIid(@Param("iid")int iid);

}
