package com.ww.gmall.pms.client;

import com.ww.gmall.pms.bean.BaseSaleAttr;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("gmall-pms")
public interface BaseSaleAttrService {
    @RequestMapping("/pms/base-sale-attr/baseSaleAttrList")
    List<BaseSaleAttr> baseSaleAttrs();
}
