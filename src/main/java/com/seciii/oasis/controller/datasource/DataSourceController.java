package com.seciii.oasis.controller.datasource;

import com.seciii.oasis.bl.datasource.DataSourceService;
import com.seciii.oasis.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/dataSource")
public class DataSourceController {

    @Autowired
    private DataSourceService dataSourceService;

    @RequestMapping(value = "/addDataSource")
    public ResponseVO addDataSource(@Valid @RequestBody DataSourceForm dataSourceForm){
        return dataSourceService.addDataSource(dataSourceForm);
    }

    @RequestMapping(value = "/updateDataSource")
    public ResponseVO updateDataSource(@Valid @RequestBody UpdateForm updateForm){
        return dataSourceService.updateDataSource(updateForm);
    }

    @RequestMapping(value = "/deleteDataSource")
    public ResponseVO deleteDataSource(@RequestParam("did")int did){
        return dataSourceService.deleteDataSource(did);
    }

    @RequestMapping(value = "/searchDataSource")
    public ResponseVO searchDataSource(@RequestParam("pname") String pname, @RequestParam("time") int time,int page){
        return dataSourceService.searchDataSourceByPname(pname,time,page);
    }

    @RequestMapping(value = "/getDataSource")
    public ResponseVO getDataSource(@RequestParam("did") int did){
        return dataSourceService.getDataSourceById(did);
    }

    @RequestMapping(value = "/getAllDataSource")
    public ResponseVO getAllDataSource(@RequestParam("page") int page){
        return dataSourceService.getAllDataSource(page);
    }

    @RequestMapping(value = "/getAttrList")
    public ResponseVO getAttrList(@RequestParam("did")int did){
        return dataSourceService.getAttrList(did);
    }

    @RequestMapping(value = "/getDatasByDid")
    public ResponseVO getDatasByDid(@RequestParam("did")int did,@RequestParam("startTime")int start,@RequestParam("endTime")int end){
        return dataSourceService.getAttrList(did);
    }

    @RequestMapping(value = "/getDatasByTime")
    public ResponseVO getDatasByTime(@Valid @RequestBody SearchDatasForm searchDatasForm){
        return dataSourceService.getDatasByTime(searchDatasForm);
    }

    @RequestMapping(value = "/getDatasByAid")
    public ResponseVO getDatasByAid(@Valid @RequestBody AidsForm aidsForm){
        return dataSourceService.getDatasByAid(aidsForm);
    }

}
