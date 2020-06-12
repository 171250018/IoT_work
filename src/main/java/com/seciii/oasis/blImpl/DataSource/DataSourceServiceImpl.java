package com.seciii.oasis.blImpl.DataSource;

import com.seciii.oasis.bl.datasource.DataSourceService;
import com.seciii.oasis.data.author.AuthorDetailMapper;
import com.seciii.oasis.data.conference.ConferenceDetailMapper;
import com.seciii.oasis.data.paper.AuthorMapper;
import com.seciii.oasis.data.paper.InstitutionMapper;
import com.seciii.oasis.data.paper.KeywordMapper;
import com.seciii.oasis.data.paper.PaperMapper;
import com.seciii.oasis.po.*;
import com.seciii.oasis.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataSourceServiceImpl implements DataSourceService {

    @Autowired
    private AuthorDetailMapper authorDetailMapper;
    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private InstitutionMapper institutionMapper;
    @Autowired
    private KeywordMapper keywordMapper;
    @Autowired
    private ConferenceDetailMapper conferenceDetailMapper;
    @Autowired
    private PaperMapper paperMapper;

    /**
     * 根据给定的pid和month，增加新的数据源，返回数据源结果
     * @param pid
     * @param month
     * @return
     */
    @Override
    public ResponseVO addDataSource(int pid,int month) {
        return ResponseVO.buildSuccess();
    }

    /**
     * 根据给定的did和month，修改数据源存储周期，返回数据源结果
     * 逻辑是搜索作者所著论文的关键字
     * @param did
     * @param month
     * @return
     */
    @Override
    public ResponseVO updateDataSource(int did,int month){
        return ResponseVO.buildSuccess();
    }

    /**
     * 根据给定的did，删除数据源，返回删除结果
     * @param did
     * @return
     */

    @Override
    public ResponseVO deleteDataSource(int did){
        return ResponseVO.buildSuccess();
    }

    /**
     * 根据给定的产品名称，查找数据源，返回数据源结果
     * @param pname
     * @return
     */

    @Override
    public ResponseVO searchDataSourceByPname(String pname){
        return ResponseVO.buildSuccess();
    }

    /**
     * 根据给定的did，查找数据源信息，返回数据源结果
     * @param did
     * @return
     */

    @Override
    public ResponseVO getDataSourceById(int did){
        return ResponseVO.buildSuccess();
    }
}
