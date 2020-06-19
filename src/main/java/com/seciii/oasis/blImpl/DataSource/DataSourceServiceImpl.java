package com.seciii.oasis.blImpl.DataSource;

import com.seciii.oasis.bl.datasource.DataSourceService;
import com.seciii.oasis.data.DataSource.DataSourceMapper;
import com.seciii.oasis.data.Product.ProductMapper;
import com.seciii.oasis.po.*;
import com.seciii.oasis.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Override
    public ResponseVO updateDataSource(UpdateForm updateForm){
        int did=updateForm.getDid();
        int month=updateForm.getMonth();
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
        try {
            dataSourceMapper.deleteDataSource(did);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            return ResponseVO.buildFailure("删除失败");
        }
    }

    /**
     * 根据给定的产品名称，查找数据源，返回数据源结果
     * @param pname
     * @return
     */

    @Override
    public ResponseVO searchDataSourceByPname(String pname,int time ,int page){
        List<DataSource> dataSourceList=new ArrayList<DataSource>();
        if(time==0){
            dataSourceList=dataSourceMapper.searchDataSourceByPname(pname,(page-1)*10);
        }
        else{
            dataSourceList=dataSourceMapper.searchDataSourceByPnameAndTime(pname,time,(page-1)*10);
        }
        List<DataSourceVO> res=new ArrayList<DataSourceVO>();
        if(dataSourceList==null||dataSourceList.size()==0){
            return ResponseVO.buildFailure("没有找到符合条件的结果");
        }
        else{
            for(DataSource d:dataSourceList){
                res.add(DataSource2VO(d));
            }
        }
        return ResponseVO.buildSuccess(res);
    }

    /**
     * 根据给定的did，查找数据源信息，返回数据源结果
     * @param did
     * @return
     */

    @Override
    public ResponseVO getDataSourceById(int did){
        DataSource dataSource=dataSourceMapper.getDataSourceById(did);
        if(dataSource==null){
            return ResponseVO.buildFailure("该数据源不存在");
        }
        return ResponseVO.buildSuccess(DataSource2VO(dataSource));
    }

    @Override
    public ResponseVO getAllDataSource(int page){
        List<DataSource> dataSourceList=dataSourceMapper.selectAllDataBase((page-1)*10);
        List<DataSourceVO> res=new ArrayList<DataSourceVO>();
        for(DataSource d:dataSourceList){
            res.add(DataSource2VO(d));
        }
        return ResponseVO.buildSuccess(res);
    }
    @Override
    public ResponseVO getAttrList(int did){
        List<AttrSimpleVO> attrSimpleVOList=dataSourceMapper.selectAttrSimple(did);
        if(attrSimpleVOList==null||attrSimpleVOList.size()==0){
            return ResponseVO.buildFailure("数据源无效");
        }
        return ResponseVO.buildSuccess(attrSimpleVOList);
    }

    @Override
    public ResponseVO getDatasByTime(SearchDatasForm searchDatasForm){
        List<Integer> aids=searchDatasForm.getAttrlist();
        int did=searchDatasForm.getDataId();
        Date start=searchDatasForm.getStart();
        Date end=searchDatasForm.getEnd();
        List<AttrDataVO> attrDataVOList=new ArrayList<AttrDataVO>();
        for(int aid:aids){
            List<Data> dataList=dataSourceMapper.selectDatasByTime(did,aid,start,end);
            Attr a=dataSourceMapper.selectAttrById(aid);
            AttrDataVO aVO=a.getVO();
            aVO.setValues(dataList);
            attrDataVOList.add(aVO);
        }
        AllDataVO allDataVO=new AllDataVO();
        allDataVO.setDid(did);

        Product p=productMapper.getProductByDId(did);
        allDataVO.setPid(p.getPid());
        allDataVO.setPname(p.getPname());
        allDataVO.setAttrDataVOList(attrDataVOList);
        return ResponseVO.buildSuccess(allDataVO);
    }

    private DataSourceVO DataSource2VO(DataSource dataSource){
        DataSourceVO dataSourceVO=new DataSourceVO();
        dataSourceVO.setDid(dataSource.getDid());
        dataSourceVO.setMonth(dataSource.getMonth());
        dataSourceVO.setPid(dataSource.getPid());
        dataSourceVO.setPname(productMapper.getProductById(dataSource.getPid()).getPname());
        dataSourceVO.setStatus(dataSource.getStatus());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        String time=dataSource.getStartTime().format(formatter);
        dataSourceVO.setStartTime(time);
        return dataSourceVO;
    }
}
