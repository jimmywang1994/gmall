package com.ww.gmall.pms.client;

import com.ww.gmall.pms.bean.SkuInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("gmall-pms")
public interface SkuInfoService {
    @RequestMapping("/pms/sku-info/saveSkuInfo")
    public String saveSkuInfo(@RequestBody SkuInfo skuInfo);
}
