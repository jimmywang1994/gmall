package com.ww.gmall.pms.controller;


import com.ww.gmall.pms.bean.ProductImage;
import com.ww.gmall.pms.bean.ProductSaleAttr;
import com.ww.gmall.pms.service.ProductSaleAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/pms/product-sale-attr")
public class ProductSaleAttrController {
    @Autowired
    ProductSaleAttrService productSaleAttrService;

    @RequestMapping("spuSaleAttrList")
    public List<ProductSaleAttr> productSaleAttrList(@RequestParam("spuId") String spuId) {
        List<ProductSaleAttr> productSaleAttrList = productSaleAttrService.productSaleAttrList(spuId);
        return productSaleAttrList;
    }

    @RequestMapping("spuImageList")
    public List<ProductImage> productImageList(@RequestParam("spuId")String spuId){
        List<ProductImage> productImageList=productSaleAttrService.productImageList(spuId);
        return productImageList;
    }
}
