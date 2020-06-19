package com.seciii.oasis.data.DataSource;

import com.seciii.oasis.po.DataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Mapper
public interface DataSourceMapper {

    int insertDataSource(DataSource dataSource);

    DataSource getDataSourceById(int did);

    List<DataSource> getUsingDataSourceByPid(int pid);

    int updateDataSource(DataSource dataSource);

    void deleteDataSource(int did);

    List<DataSource> searchDataSourceByPname(String pname,int begin);

    List<DataSource> searchDataSourceByPnameAndTime(String pname,int time,int begin);

    List<DataSource> selectAllDataBase(int begin);

    @Scheduled(cron = "0/1 * * * * ?")
    void cleanExpiredDataSource();

}
