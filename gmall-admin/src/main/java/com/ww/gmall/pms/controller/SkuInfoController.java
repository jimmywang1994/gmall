package com.ww.gmall.pms.controller;


import com.ww.gmall.pms.bean.SkuInfo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 库存单元表 前端控制器
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@RestController
@CrossOrigin
@RequestMapping("/pms/sku-info")
public class SkuInfoController {

    @RequestMapping("saveSkuInfo")
    public String saveSkuInfo(@RequestBody SkuInfo skuInfo){
        return null;
    }
}
