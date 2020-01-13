package com.ww.gmall.pms.controller;


import com.ww.gmall.pms.bean.BaseAttrInfo;
import com.ww.gmall.pms.client.BaseAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin
@RequestMapping("/pms/base-attr-info")
public class BaseAttrInfoController {
    @Autowired
    BaseAttrService baseAttrService;

    @RequestMapping("attrInfoList")
    public List<BaseAttrInfo> attrInfoList(@RequestParam("catalog3Id")String catalog3Id){
        return baseAttrService.attrInfoList(catalog3Id);
    }

    @RequestMapping("saveAttrInfo")
    public String saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){
        return baseAttrService.saveAttrInfo(baseAttrInfo);
    }

    @RequestMapping("deleteAttrInfoById/{attrId}")
    public String deleteAttrInfoById(@PathVariable("attrId")String attrId){
        return baseAttrService.deleteAttrInfoById(attrId);
    }
}
