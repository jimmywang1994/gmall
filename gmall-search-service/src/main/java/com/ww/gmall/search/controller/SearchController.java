package com.ww.gmall.search.controller;

import com.ww.gmall.pms.bean.SearchSkuInfo;
import com.ww.gmall.pms.bean.SkuInfo;
import com.ww.gmall.pms.bean.SkuInfoParam;
import com.ww.gmall.pms.service.SearchService;
import com.ww.gmall.search.client.SkuClient;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import jodd.bean.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search-service")
public class SearchController {
    @Autowired
    SearchService searchService;
    @Autowired
    JestClient jestClient;
    @Autowired
    SkuClient skuClient;

    @RequestMapping("list")
    public List<SearchSkuInfo> list(@RequestParam(required = false)String catalog3Id,
                                    @RequestParam(required = false)String keyword,
                                    @RequestParam(required = false)String[] valueIds) {
        List<SearchSkuInfo> searchSkuInfoList = new ArrayList<>();
        searchSkuInfoList = searchService.list(catalog3Id,keyword,valueIds);
        return searchSkuInfoList;
    }

    /**
     * mysql数据导入es
     */
    @RequestMapping("import")
    public void importEs() {
        List<SkuInfo> skuInfoList = new ArrayList<>();
        skuInfoList = skuClient.getAllSku("61");
        List<SearchSkuInfo> searchSkuInfoList = new ArrayList<>();
        for (SkuInfo skuInfo : skuInfoList) {
            SearchSkuInfo searchSkuInfo = new SearchSkuInfo();
            BeanUtils.copyProperties(skuInfo, searchSkuInfo);
            searchSkuInfo.setId(Long.parseLong(skuInfo.getId()));
            searchSkuInfoList.add(searchSkuInfo);
        }
        for (SearchSkuInfo searchSkuInfo : searchSkuInfoList) {
            Index put = new Index.Builder(searchSkuInfo).index("gmall").type("SkuInfo").id(searchSkuInfo.getId() + "").build();
            try {
                jestClient.execute(put);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
