package com.ww.gmall.pms.client;

import com.ww.gmall.pms.bean.ProductImage;
import com.ww.gmall.pms.bean.ProductSaleAttr;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("gmall-pms")
public interface ProductSaleAttrService {
    @RequestMapping("/pms/product-sale-attr/spuSaleAttrList")
    public List<ProductSaleAttr> productSaleAttrList(@RequestParam("spuId") String spuId);

    @RequestMapping("/pms/product-sale-attr/spuImageList")
    public List<ProductImage> productImageList(@RequestParam("spuId") String spuId);
}
