package com.ww.gmall.item.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("gmall-pms")
public interface SkuInfoService {

}
