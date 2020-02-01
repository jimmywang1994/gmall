package com.ww.gmall.search.controller;

import com.ww.gmall.pms.bean.BaseAttrInfo;
import com.ww.gmall.pms.bean.SearchSkuInfo;
import com.ww.gmall.pms.bean.SkuAttrValue;
import com.ww.gmall.pms.bean.SkuInfoParam;
import com.ww.gmall.search.client.SearchClient;
import com.ww.gmall.search.client.SkuClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SearchController {
    @Autowired
    SearchClient searchClient;
    @Autowired
    SkuClient skuClient;

    @RequestMapping("index")
    public String index() {
        return "index";
    }

    @RequestMapping("list.html")
    public String list(@RequestParam(required = false) String catalog3Id,
                       @RequestParam(required = false) String keyword,
                       ModelMap modelMap) {
        List<SearchSkuInfo> searchSkuInfoList = searchClient.searchSkuInfoList(catalog3Id, keyword);
        modelMap.put("skuLsInfoList", searchSkuInfoList);
        //set 无序不重复，用来抽取平台属性集合
        Set<String> valueIdSet = new HashSet<>();
        for (SearchSkuInfo searchSkuInfo : searchSkuInfoList) {
            List<SkuAttrValue> skuAttrValueList = searchSkuInfo.getSkuAttrValueList();
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                String valueId = skuAttrValue.getValueId();
                valueIdSet.add(valueId);
            }
        }
        List<BaseAttrInfo> baseAttrInfoList = skuClient.getAttrValueByAttrId(valueIdSet);
        modelMap.put("attrList", baseAttrInfoList);
        return "list";
    }
}
