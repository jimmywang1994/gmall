package com.ww.gmall.pms.client;

import com.ww.gmall.pms.bean.BaseCatalog1;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "gmall-pms")
public interface BaseCatalogService {
    @RequestMapping("/pms/base-catalog1/getCatalog1s")
    public List<BaseCatalog1> getCatalog1s();
}
