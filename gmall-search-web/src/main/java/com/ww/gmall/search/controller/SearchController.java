package com.ww.gmall.search.controller;

import com.ww.gmall.pms.bean.*;
import com.ww.gmall.search.client.SearchClient;
import com.ww.gmall.search.client.SkuClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Iterator;
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
                       @RequestParam(required = false) String[] valueIds,
                       ModelMap modelMap) {
        List<SearchSkuInfo> searchSkuInfoList = searchClient.searchSkuInfoList(catalog3Id, keyword, valueIds);
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
        //对平台属性集合进一步处理，去除已被选中的平台属性组
        if (valueIds != null) {
            Iterator<BaseAttrInfo> attrInfoIterator = baseAttrInfoList.iterator();
            while (attrInfoIterator.hasNext()) {
                BaseAttrInfo baseAttrInfo = attrInfoIterator.next();
                List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
                for (BaseAttrValue attrValue : attrValueList) {
                    String valueId = attrValue.getId().toString();
                    for (String delId : valueIds) {
                        if (delId.equals(valueId)) {
                            //删除该属性值所在的属性组
                            attrInfoIterator.remove();
                        }
                    }
                }
            }
        }
        String urlParam = getUrlParam(catalog3Id, keyword, valueIds);
        modelMap.put("urlParam", urlParam);
        return "list";
    }

    private String getUrlParam(String catalog3Id, String keyword, String[] valueIds) {
        String urlParam = "";
        if (StringUtils.isNotBlank(catalog3Id)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "catalog3Id=" + catalog3Id;
        }
        if (StringUtils.isNotBlank(keyword)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "keyword=" + keyword;
        }
        if (valueIds != null) {
            for (String valueId : valueIds) {
                urlParam = urlParam + "&valueIds=" + valueId;
            }
        }
        return urlParam;
    }
}
