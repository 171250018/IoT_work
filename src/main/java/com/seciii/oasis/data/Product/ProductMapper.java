package com.seciii.oasis.data.Product;

import com.seciii.oasis.po.DataSource;
import com.seciii.oasis.po.Product;
import com.seciii.oasis.vo.ProductSimpleVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    Product getProductById(int pid);

    List<ProductSimpleVO> getUnUsedProduct();
}
