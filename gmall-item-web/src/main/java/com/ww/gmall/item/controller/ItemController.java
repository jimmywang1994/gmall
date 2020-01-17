package com.ww.gmall.item.controller;

import com.ww.gmall.item.client.SkuClient;
import com.ww.gmall.pms.bean.SkuInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {
    @Autowired
    SkuClient skuClient;

    @RequestMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("hello", "thymleaf");
        return "index";
    }

    @RequestMapping("{skuId}.html")
    public String items(@PathVariable("skuId") String skuId, ModelMap modelMap) {
        SkuInfo skuInfo = skuClient.skuById(skuId);
        modelMap.put("skuInfo", skuInfo);
        return "item";
    }
}
