package com.ww.gmall.item;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("gmall-pms")
public interface SkuInfoService {

}
