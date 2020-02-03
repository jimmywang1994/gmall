package com.ww.gmall.pms.service;

import com.ww.gmall.pms.bean.SearchSkuInfo;
import com.ww.gmall.pms.bean.SkuInfoParam;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

public interface SearchService {
    /**
     * es查询出的结果
     * @param
     * @return
     */
    List<SearchSkuInfo> list(String catalog3Id,
                             String keyword,
                             String[] valueIds);
}
