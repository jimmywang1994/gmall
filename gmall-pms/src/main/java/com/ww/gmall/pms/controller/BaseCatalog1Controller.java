package com.ww.gmall.pms.controller;


import com.ww.gmall.pms.bean.BaseCatalog1;
import com.ww.gmall.pms.mapper.BaseCatalog1Mapper;
import com.ww.gmall.pms.service.BaseCatalog1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 一级分类表 前端控制器
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/pms/base-catalog1")
public class BaseCatalog1Controller {
    @Autowired
    BaseCatalog1Service baseCatalog1Service;
    @RequestMapping("getCatalog1s")
    public List<BaseCatalog1> catalog1s(){
        List<BaseCatalog1> baseCatalog1s=baseCatalog1Service.getCatalog1s();
        return baseCatalog1s;
    }
}
