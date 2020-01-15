package com.ww.gmall.pms.controller;


import com.ww.gmall.pms.bean.ProductInfo;
import com.ww.gmall.pms.client.ProductInfoService;
import com.ww.gmall.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
@CrossOrigin
@RequestMapping("/pms/product-info")
public class ProductInfoController {
    @Autowired
    ProductInfoService productInfoService;

    @RequestMapping("spuList")
    public List<ProductInfo> productInfos(@RequestParam("catalog3Id") String catalog3Id) {
        return productInfoService.productInfos(catalog3Id);
    }

    @RequestMapping("saveSpuInfo")
    public String saveSpuInfo(@RequestBody ProductInfo productInfo) {
        productInfoService.saveSpuInfo(productInfo);
        return "success";
    }

    @RequestMapping("fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile) {
        String imgUrl = UploadUtil.uploadUtil(multipartFile);
        return imgUrl;
    }
}
