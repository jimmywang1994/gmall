package com.ww.gmall.pms.controller;


import com.ww.gmall.pms.bean.BaseAttrValue;
import com.ww.gmall.pms.client.BaseAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
@RequestMapping("/pms/base-attr-value")
public class BaseAttrValueController {
    @Autowired
    BaseAttrService baseAttrService;

    @RequestMapping("getAttrValueList")
    public List<BaseAttrValue> getAttrValueList(@RequestParam("attrId") String attrId) {
        return baseAttrService.getAttrValueList(attrId);
    }
}
