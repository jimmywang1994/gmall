package com.ww.gmall.search.client;

import com.ww.gmall.pms.bean.SearchSkuInfo;
import com.ww.gmall.pms.bean.SkuInfoParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("gmall-search-service")
public interface SearchClient {
    @RequestMapping("/search-service/list")
    public List<SearchSkuInfo> searchSkuInfoList(@RequestParam(required = false)String catalog3Id,
                                                 @RequestParam(required = false)String keyword);
}
