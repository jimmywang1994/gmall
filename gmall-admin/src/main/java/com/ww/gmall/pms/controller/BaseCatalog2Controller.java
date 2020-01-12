package com.ww.gmall.pms.controller;


import com.ww.gmall.pms.bean.BaseCatalog1;
import com.ww.gmall.pms.bean.BaseCatalog2;
import com.ww.gmall.pms.client.BaseCatalogService;
import com.ww.gmall.pms.service.BaseCatalog2Service;
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
@RequestMapping("/pms/base-catalog2")
public class BaseCatalog2Controller {

    @Autowired
    BaseCatalogService baseCatalogService;

    @RequestMapping("getCatalog2")
    public List<BaseCatalog2> catalog2(@RequestParam("catalog1Id")String catalog1Id){
        return baseCatalogService.getCatalog2s(catalog1Id);
    }
}
