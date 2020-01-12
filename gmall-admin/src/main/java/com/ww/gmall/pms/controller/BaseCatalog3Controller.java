package com.ww.gmall.pms.controller;


import com.netflix.discovery.converters.Auto;
import com.ww.gmall.pms.bean.BaseCatalog3;
import com.ww.gmall.pms.client.BaseCatalogService;
import com.ww.gmall.pms.service.BaseCatalog3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
@RequestMapping("/pms/base-catalog3")
public class BaseCatalog3Controller {
    @Autowired
    BaseCatalogService baseCatalogService;

    @RequestMapping("getCatalog3")
    public List<BaseCatalog3> getCatalog3s(@RequestParam("catalog2Id")String catalog2Id){
        return baseCatalogService.getCatalog3s(catalog2Id);
    }
}
