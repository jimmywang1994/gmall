package com.ww.gmall.pms.controller;


import com.ww.gmall.pms.bean.ProductInfo;
import com.ww.gmall.pms.client.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
@RequestMapping("/pms/product-info")
public class ProductInfoController {
    @Autowired
    ProductInfoService productInfoService;

    @RequestMapping("spuList")
    public List<ProductInfo> productInfos(@RequestParam("catalog3Id") String catalog3Id) {
        return productInfoService.productInfos(catalog3Id);
    }
}
