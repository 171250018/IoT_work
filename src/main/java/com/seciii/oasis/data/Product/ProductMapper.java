package com.seciii.oasis.data.Product;

import com.seciii.oasis.po.DataSource;
import com.seciii.oasis.po.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {

    Product getProductById(int pid);

}
