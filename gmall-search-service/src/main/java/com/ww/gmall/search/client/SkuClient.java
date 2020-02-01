package com.ww.gmall.search.client;

import com.ww.gmall.pms.bean.SkuInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("gmall-pms")
public interface SkuClient {
    @RequestMapping("/pms/sku-info/getAllSku")
    public List<SkuInfo> getAllSku(@RequestParam("catalog3Id")String catalog3Id);
}
