package com.ww.gmall.search.controller;

import com.ww.gmall.pms.bean.SearchSkuInfo;
import com.ww.gmall.pms.bean.SkuInfoParam;
import com.ww.gmall.search.client.SearchClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SearchController {
    @Autowired
    SearchClient searchClient;

    @RequestMapping("index")
    public String index() {
        return "index";
    }

    @RequestMapping("list.html")
    public String list(@RequestParam(required = false)String catalog3Id,
                       @RequestParam(required = false)String keyword,
                       ModelMap modelMap) {
        List<SearchSkuInfo> searchSkuInfoList = searchClient.searchSkuInfoList(catalog3Id,keyword);
        modelMap.put("skuLsInfoList", searchSkuInfoList);
        return "list";
    }
}
