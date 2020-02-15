package com.ww.gmall.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient("gmall-pms")
public interface SkuClient {
    @RequestMapping("/pms/sku-info/checkPrice")
    public boolean checkPrice(@RequestParam("skuId") String skuId, @RequestParam("price") BigDecimal price);
}
