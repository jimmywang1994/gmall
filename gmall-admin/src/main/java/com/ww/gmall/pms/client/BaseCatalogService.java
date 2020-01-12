package com.ww.gmall.pms.client;

import com.ww.gmall.pms.bean.BaseCatalog1;
import com.ww.gmall.pms.bean.BaseCatalog2;
import com.ww.gmall.pms.bean.BaseCatalog3;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "gmall-pms")
public interface BaseCatalogService {
    @RequestMapping("/pms/base-catalog1/getCatalog1s")
    public List<BaseCatalog1> getCatalog1s();

    @RequestMapping("/pms/base-catalog2/getCatalog2")
    public List<BaseCatalog2> getCatalog2s(@RequestParam("catalog1Id") String catalog1Id);

    @RequestMapping("/pms/base-catalog3/getCatalog3")
    public List<BaseCatalog3> getCatalog3s(@RequestParam("catalog2Id") String catalog2Id);
}
