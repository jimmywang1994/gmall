package com.ww.gmall.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.pms.bean.BaseCatalog2;
import com.ww.gmall.pms.mapper.BaseCatalog2Mapper;
import com.ww.gmall.pms.service.BaseCatalog2Service;
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
public class BaseCatalog2ServiceImpl extends ServiceImpl<BaseCatalog2Mapper, BaseCatalog2> implements BaseCatalog2Service {

    @Autowired
    BaseCatalog2Mapper baseCatalog2Mapper;

    @Override
    public List<BaseCatalog2> getCatalog2s(String catalog1Id) {
        QueryWrapper<BaseCatalog2> wrapper = new QueryWrapper<>();
        wrapper.eq("catalog1_id", catalog1Id);
        return baseCatalog2Mapper.selectList(wrapper);
    }
}
