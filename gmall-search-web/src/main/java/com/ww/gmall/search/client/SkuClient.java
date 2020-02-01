package com.ww.gmall.search.client;

import com.ww.gmall.pms.bean.BaseAttrInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@FeignClient("gmall-pms")
public interface SkuClient {
    @RequestMapping("/pms/base-attr-info/getAttrValueByAttrId")
    public List<BaseAttrInfo> getAttrValueByAttrId(@RequestParam("valueIdSet") Set<String> valueIdSet);
}
