package com.seciii.oasis.blImpl.DataSource;

import com.seciii.oasis.bl.datasource.DataSourceService;
import com.seciii.oasis.data.DataSource.DataSourceMapper;
import com.seciii.oasis.data.Product.ProductMapper;
import com.seciii.oasis.po.*;
import com.seciii.oasis.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DataSourceServiceImpl implements DataSourceService {

    @Autowired
    private DataSourceMapper dataSourceMapper;
    @Autowired
    private ProductMapper productMapper;

    /**
     * 根据给定的pid和month，增加新的数据源，返回数据源结果
     * @param
     * @param
     * @return
     */
    @Override
    public ResponseVO addDataSource(DataSourceForm dataSourceForm) {
        List<DataSource> mid=dataSourceMapper.getUsingDataSourceByPid(dataSourceForm.getPid());
        if(mid!=null&&mid.size()!=0){
            return ResponseVO.buildFailure("产品已存在数据源");
        }
        DataSource dataSource=new DataSource();
        dataSource.setMonth(dataSourceForm.getMonth());
        dataSource.setPid(dataSourceForm.getPid());
        dataSource.setStatus(0);
        dataSourceMapper.insertDataSource(dataSource);
        DataSource newDataSource=dataSourceMapper.getDataSourceById(dataSource.getDid());
        DataSourceVO dataSourceVO=DataSource2VO(newDataSource);
        return ResponseVO.buildSuccess(dataSourceVO);
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
        DataSource dataSource=dataSourceMapper.getDataSourceById(did);
        if(dataSource==null){
            return ResponseVO.buildFailure("数据源不存在");
        }
        else if(dataSource.getStatus()==1){
            return ResponseVO.buildFailure("数据源已过期");
        }
        else if(dataSource.getStatus()==2){
            return ResponseVO.buildFailure("数据源已停用");
        }
        dataSource.setMonth(month);
        dataSourceMapper.updateDataSource(dataSource);

        return ResponseVO.buildSuccess(DataSource2VO(dataSource));
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

    private DataSourceVO DataSource2VO(DataSource dataSource){
        DataSourceVO dataSourceVO=new DataSourceVO();
        dataSourceVO.setDid(dataSource.getDid());
        dataSourceVO.setMonth(dataSource.getMonth());
        dataSourceVO.setPid(dataSource.getPid());
        dataSourceVO.setPname(productMapper.getProductById(dataSource.getPid()).getPname());
        dataSourceVO.setStatus(dataSource.getStatus());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time=dataSource.getStartTime().format(formatter);
        dataSourceVO.setStartTime(time);
        return dataSourceVO;
    }
}
