package com.ww.gmall.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.pms.bean.BaseCatalog3;
import com.ww.gmall.pms.mapper.BaseCatalog3Mapper;
import com.ww.gmall.pms.service.BaseCatalog3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@Service
public class BaseCatalog3ServiceImpl extends ServiceImpl<BaseCatalog3Mapper, BaseCatalog3> implements BaseCatalog3Service {

    @Autowired
    BaseCatalog3Mapper baseCatalog3Mapper;

    @Override
    public List<BaseCatalog3> getCatalog3s(String catalog2Id) {
        QueryWrapper<BaseCatalog3> wrapper=new QueryWrapper<>();
        wrapper.eq("catalog2_id",catalog2Id);
        return baseCatalog3Mapper.selectList(wrapper);
    }
}
