package com.seciii.oasis.blImpl.product;

import com.seciii.oasis.bl.product.ProductService;
import com.seciii.oasis.data.Product.ProductMapper;
import com.seciii.oasis.po.Product;
import com.seciii.oasis.vo.DataSourceForm;
import com.seciii.oasis.vo.DataSourceVO;
import com.seciii.oasis.vo.ProductSimpleVO;
import com.seciii.oasis.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseVO getUnUsedProduct(){
        List<ProductSimpleVO> simpleVOList=productMapper.getUnUsedProduct();
        return ResponseVO.buildSuccess(simpleVOList);
    }
}
