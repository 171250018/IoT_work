package com.seciii.oasis.bl.datasource;

import com.seciii.oasis.vo.DataSourceForm;
import com.seciii.oasis.vo.ResponseVO;
import com.seciii.oasis.vo.UpdateForm;

public interface DataSourceService {

    ResponseVO addDataSource(DataSourceForm dataSourceForm);

    ResponseVO updateDataSource(UpdateForm updateForm);

    ResponseVO deleteDataSource(int did);

    ResponseVO searchDataSourceByPname(String pname,int time,int page);

    ResponseVO getDataSourceById(int did);

    ResponseVO getAllDataSource(int page);

}
