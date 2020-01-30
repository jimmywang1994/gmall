package com.ww.gmall.pms.service;

import com.ww.gmall.pms.bean.SearchSkuInfo;
import com.ww.gmall.pms.bean.SkuInfoParam;

import java.io.IOException;
import java.util.List;

public interface SearchService {
    /**
     * es查询出的结果
     * @param skuInfoParam
     * @return
     */
    List<SearchSkuInfo> list(SkuInfoParam skuInfoParam);
}
