package com.ww.gmall.cart.client;

import com.ww.gmall.pms.bean.SkuInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("gmall-pms")
public interface SkuClient {
    @RequestMapping("/pms/sku-info/skuById/{skuId}")
    public SkuInfo skuById(@PathVariable("skuId") String skuId, String ip);
}
