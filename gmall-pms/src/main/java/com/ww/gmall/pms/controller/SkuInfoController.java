package com.ww.gmall.pms.controller;


import com.ww.gmall.pms.bean.SkuInfo;
import com.ww.gmall.pms.service.SkuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 库存单元表 前端控制器
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/pms/sku-info")
public class SkuInfoController {
    @Autowired
    SkuInfoService skuInfoService;

    @RequestMapping("saveSkuInfo")
    public String saveSkuInfo(@RequestBody SkuInfo skuInfo) {
        String result = skuInfoService.saveSkuInfo(skuInfo);
        return result;
    }

    @RequestMapping("skuById/{skuId}")
    public SkuInfo skuById(@PathVariable("skuId") String skuId) {
        SkuInfo skuInfo = skuInfoService.skuById(skuId);
        return skuInfo;
    }
}
