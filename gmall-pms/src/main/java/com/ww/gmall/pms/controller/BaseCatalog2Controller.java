package com.ww.gmall.pms.controller;


import com.ww.gmall.pms.bean.BaseCatalog2;
import com.ww.gmall.pms.service.BaseCatalog2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/pms/base-catalog2")
public class BaseCatalog2Controller {

    @Autowired
    BaseCatalog2Service baseCatalog2Service;

    @RequestMapping("getCatalog2")
    public List<BaseCatalog2> getCatalogByCatalog1Id(@RequestParam("catalog1Id")String catalog1Id){
        List<BaseCatalog2> baseCatalog2s=baseCatalog2Service.getCatalog2s(catalog1Id);
        return baseCatalog2s;
    }
}
