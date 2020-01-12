package com.ww.gmall.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.pms.bean.BaseCatalog1;
import com.ww.gmall.pms.mapper.BaseCatalog1Mapper;
import com.ww.gmall.pms.service.BaseCatalog1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 一级分类表 服务实现类
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@Service
public class BaseCatalog1ServiceImpl extends ServiceImpl<BaseCatalog1Mapper, BaseCatalog1> implements BaseCatalog1Service {

    @Autowired
    BaseCatalog1Mapper baseCatalog1Mapper;


    @Override
    public List<BaseCatalog1> getCatalog1s() {
        return baseCatalog1Mapper.selectList(null);
    }
}
