package com.seciii.oasis.bl.datasource;

import com.seciii.oasis.vo.*;

public interface DataSourceService {

    ResponseVO addDataSource(DataSourceForm dataSourceForm);

    ResponseVO updateDataSource(UpdateForm updateForm);

    ResponseVO deleteDataSource(int did);

    ResponseVO searchDataSourceByPname(String pname,int time,int page);

    ResponseVO getDataSourceById(int did);

    ResponseVO getAllDataSource(int page);

    ResponseVO getAttrList(int did);

    ResponseVO getDatasByTime(SearchDatasForm searchDatasForm);

    ResponseVO getDatasByAid(AidsForm aidsForm);

}
