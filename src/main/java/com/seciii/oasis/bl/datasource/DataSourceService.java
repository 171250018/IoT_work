package com.seciii.oasis.bl.datasource;

import com.seciii.oasis.vo.ResponseVO;

public interface DataSourceService {

    ResponseVO addDataSource(int pid,int month);

    ResponseVO updateDataSource(int did,int month);

    ResponseVO deleteDataSource(int did);

    ResponseVO searchDataSourceByPname(String pname);

    ResponseVO getDataSourceById(int did);

}
