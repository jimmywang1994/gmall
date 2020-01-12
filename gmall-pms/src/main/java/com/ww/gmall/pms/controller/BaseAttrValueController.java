package com.ww.gmall.pms.controller;


import com.ww.gmall.pms.bean.BaseAttrValue;
import com.ww.gmall.pms.service.BaseAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 属性值表 前端控制器
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/pms/base-attr-value")
public class BaseAttrValueController {
    @Autowired
    BaseAttrValueService baseAttrValueService;

    @RequestMapping("getAttrValueList")
    public List<BaseAttrValue> attrValueList(@RequestParam("attrId")String attrId){
        List<BaseAttrValue> attrValueList=baseAttrValueService.baseAttrValues(attrId);
        return attrValueList;
    }
}
