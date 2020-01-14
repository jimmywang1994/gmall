package com.ww.gmall.pms.controller;


import com.netflix.discovery.converters.Auto;
import com.ww.gmall.pms.bean.ProductInfo;
import com.ww.gmall.pms.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping("/pms/product-info")
public class ProductInfoController {
    @Autowired
    ProductInfoService productInfoService;

    @RequestMapping("spuList")
    public List<ProductInfo> productInfos(@RequestParam("catalog3Id") String catalog3Id) {
        List<ProductInfo> productInfos = productInfoService.produceInfos(catalog3Id);
        return productInfos;
    }

    @RequestMapping("saveSpuInfo")
    public String saveSpuInfo(@RequestBody ProductInfo productInfo) {
        productInfoService.saveSpuInfo(productInfo);
        return "success";
    }

    @RequestMapping(value = "fileUpload",produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile) {
        return productInfoService.uploadFile(multipartFile);
    }
}
