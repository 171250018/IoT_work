package com.seciii.oasis.controller.product;

import com.seciii.oasis.bl.product.ProductService;
import com.seciii.oasis.vo.DataSourceForm;
import com.seciii.oasis.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/unUsedProduct")
    public ResponseVO getUnUsedProduct(){
        return productService.getUnUsedProduct();
    }

}
