package com.ww.gmall.pms.controller;


import com.ww.gmall.pms.bean.BaseAttrInfo;
import com.ww.gmall.pms.service.BaseAttrInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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

    @RequestMapping("saveAttrInfo")
    public String saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo) {
        String result = baseAttrInfoService.saveAttr(baseAttrInfo);
        return result;
    }

    @RequestMapping("deleteAttrInfoById/{attrId}")
    public String deleteAttrInfoById(@PathVariable("attrId") String attrId) {
        String result = baseAttrInfoService.deleAttr(attrId);
        return result;
    }

    @RequestMapping("getAttrValueByAttrId")
    public List<BaseAttrInfo> getAttrValueByAttrId(@RequestParam("valueIdSet") Set<String> valueIdSet) {
        List<BaseAttrInfo> attrInfoList = baseAttrInfoService.getAttrValueByAttrId(valueIdSet);
        return attrInfoList;
    }
}
