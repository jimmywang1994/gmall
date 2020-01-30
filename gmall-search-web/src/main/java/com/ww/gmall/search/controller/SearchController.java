package com.ww.gmall.search.controller;

import com.ww.gmall.pms.bean.SkuInfoParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @RequestMapping("index")
    public String index() {
        return "index";
    }

    @RequestMapping("list.html")
    public String list(SkuInfoParam param) {
        return "list";
    }
}
