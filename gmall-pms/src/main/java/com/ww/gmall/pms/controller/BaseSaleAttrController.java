package com.ww.gmall.pms.controller;


import com.netflix.discovery.converters.Auto;
import com.ww.gmall.pms.bean.BaseSaleAttr;
import com.ww.gmall.pms.service.BaseSaleAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/pms/base-sale-attr")
public class BaseSaleAttrController {
    @Autowired
    BaseSaleAttrService baseSaleAttrService;

    @RequestMapping("baseSaleAttrList")
    public List<BaseSaleAttr> baseSaleAttrList() {
        return baseSaleAttrService.baseSaleAttrs();
    }
}
