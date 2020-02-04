package com.ww.gmall.search.controller;

import com.ww.gmall.pms.bean.*;
import com.ww.gmall.search.client.SearchClient;
import com.ww.gmall.search.client.SkuClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
            //面包屑
            List<SearchCrumb> searchCrumbList = new ArrayList<>();
            for (String delId : valueIds) {
                Iterator<BaseAttrInfo> attrInfoIterator = baseAttrInfoList.iterator();//平台属性集合
                SearchCrumb searchCrumb = new SearchCrumb();
                //生成面包屑的参数
                searchCrumb.setValueId(delId);
                searchCrumb.setUrlParam(getUrlParam(catalog3Id, keyword, valueIds, delId));
                while (attrInfoIterator.hasNext()) {
                    BaseAttrInfo baseAttrInfo = attrInfoIterator.next();
                    List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
                    for (BaseAttrValue attrValue : attrValueList) {
                        String valueId = attrValue.getId().toString();
                        if (delId.equals(valueId)) {
                            //查找面包屑的属性名
                            searchCrumb.setValueName(attrValue.getValueName());
                            //删除该属性值所在的属性组
                            attrInfoIterator.remove();
                        }
                    }
                }
                searchCrumbList.add(searchCrumb);
            }
            modelMap.put("attrValueSelectedList", searchCrumbList);
        }
        String urlParam = getUrlParam(catalog3Id, keyword, valueIds);
        modelMap.put("urlParam", urlParam);
        if (StringUtils.isNotBlank(keyword)) {
            modelMap.put("keyword", keyword);
        }
        return "list";
    }

    private String getUrlParam(String catalog3Id, String keyword, String[] valueIds, String... delValueId) {
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
            for (String skuAttrValue : valueIds) {
                //当前url参数，当面包屑请求中含有当前valueId时，不把当前valueId加到url参数中
                if (delValueId != null && delValueId.length > 0) {
                    if (!skuAttrValue.equals(delValueId[0])) {
                        urlParam = urlParam + "&valueIds=" + skuAttrValue;
                    }
                } else {
                    urlParam = urlParam + "&valueIds=" + skuAttrValue;
                }
            }

        }
        return urlParam;
    }
}
