package com.ww.gmall.pms.controller;


import com.ww.gmall.pms.bean.BaseAttrInfo;
import com.ww.gmall.pms.service.BaseAttrInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 属性表 前端控制器
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/pms/base-attr-info")
public class BaseAttrInfoController {
    @Autowired
    BaseAttrInfoService baseAttrInfoService;

    @RequestMapping("attrInfoList")
    public List<BaseAttrInfo> attrInfoList(@RequestParam("catalog3Id") String catalog3Id) {
        List<BaseAttrInfo> attrInfos = baseAttrInfoService.baseAttrInfos(catalog3Id);
        return attrInfos;
    }
}
