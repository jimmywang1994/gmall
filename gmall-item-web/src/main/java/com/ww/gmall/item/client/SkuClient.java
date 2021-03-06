package com.ww.gmall.item.client;

import com.ww.gmall.pms.bean.ProductSaleAttr;
import com.ww.gmall.pms.bean.SkuInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("gmall-pms")
public interface SkuClient {
    @RequestMapping("/pms/sku-info/skuById/{skuId}")
    public SkuInfo skuById(@PathVariable("skuId") String skuId,String ip);

    @RequestMapping("/pms/product-sale-attr/productSaleAttrListCheckBySku")
    public List<ProductSaleAttr> productSaleAttr(@RequestParam("productId") String productId, @RequestParam("skuId") String skuId);

    @RequestMapping("/pms/sku-info/getSkuSaleAttrValueListBySku")
    public String getSkuSaleAttrValueListBySku(@RequestParam("productId") String productId);
}
