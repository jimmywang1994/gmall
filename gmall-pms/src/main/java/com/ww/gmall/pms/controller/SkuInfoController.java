package com.ww.gmall.pms.controller;


import com.alibaba.fastjson.JSON;
import com.ww.gmall.pms.bean.SkuInfo;
import com.ww.gmall.pms.bean.SkuSaleAttrValue;
import com.ww.gmall.pms.service.SkuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping("getSkuSaleAttrValueListBySku")
    public String getSkuSaleAttrValueListBySku(@RequestParam("productId") String productId) {
        List<SkuInfo> skuInfoList = skuInfoService.getSkuSaleAttrValueListBySku(productId);
        Map<String, Object> skuInfoHashMap = new HashMap<>();
        for (SkuInfo skuInfo : skuInfoList) {
            String k = "";
            String v = skuInfo.getId().toString();
            List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
            for (SkuSaleAttrValue saleAttrValue : skuSaleAttrValueList) {
                k += saleAttrValue.getSaleAttrValueId() + "|";
            }
            skuInfoHashMap.put(k, v);
        }
        String skuSaleAttrListJson = JSON.toJSONString(skuInfoHashMap);
        return skuSaleAttrListJson;
    }
}
