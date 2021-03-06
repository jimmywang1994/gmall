package com.ww.gmall.pms.client;

import com.ww.gmall.pms.bean.BaseAttrInfo;
import com.ww.gmall.pms.bean.BaseAttrValue;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("gmall-pms")
public interface BaseAttrService {
    @RequestMapping("/pms/base-attr-info/attrInfoList")
    public List<BaseAttrInfo> attrInfoList(@RequestParam("catalog3Id")String catalog3Id);

    @RequestMapping("/pms/base-attr-value/getAttrValueList")
    public List<BaseAttrValue> getAttrValueList(@RequestParam("attrId")String attrId);

    @RequestMapping("/pms/base-attr-info/saveAttrInfo")
    public String saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo);

    @RequestMapping("/pms/base-attr-info/deleteAttrInfoById/{attrId}")
    public String deleteAttrInfoById(@PathVariable("attrId") String attrId);
}
