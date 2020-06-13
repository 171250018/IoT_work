package com.seciii.oasis.data.DataSource;

import com.seciii.oasis.po.DataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DataSourceMapper {

    int insertDataSource(DataSource dataSource);

    DataSource getDataSourceById(int did);

    List<DataSource> getUsingDataSourceByPid(int pid);

    int updateDataSource(DataSource dataSource);

}
