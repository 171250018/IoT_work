package com.seciii.oasis.data.statistics;

import com.seciii.oasis.po.InstitutionCount;
import com.seciii.oasis.po.Paper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StatisticsMapper {

    List<Paper> selectTopRefPaper();

    List<InstitutionCount> selectTopPaperCountInstitutionIid();

}
