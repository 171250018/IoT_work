package com.seciii.oasis.controller.datasource;

import com.seciii.oasis.bl.author.AuthorService;
import com.seciii.oasis.bl.datasource.DataSourceService;
import com.seciii.oasis.vo.DataSourceForm;
import com.seciii.oasis.vo.ResponseVO;
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
    private AuthorService authorService;

    @Autowired
    private DataSourceService dataSourceService;

    @RequestMapping(value = "/addDataSource")
    public ResponseVO addDataSource(@Valid @RequestBody DataSourceForm dataSourceForm){
        return dataSourceService.addDataSource(dataSourceForm);
    }

    @RequestMapping(value = "/updateDataSource")
    public ResponseVO updateDataSource(@RequestParam("did")int did,@RequestParam("month")int month){
        return dataSourceService.updateDataSource(did,month);
    }

}
