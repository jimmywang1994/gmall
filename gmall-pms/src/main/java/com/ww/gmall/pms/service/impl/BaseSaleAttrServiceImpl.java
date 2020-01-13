package com.ww.gmall.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.pms.bean.BaseSaleAttr;
import com.ww.gmall.pms.mapper.BaseSaleAttrMapper;
import com.ww.gmall.pms.service.BaseSaleAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@Service
public class BaseSaleAttrServiceImpl extends ServiceImpl<BaseSaleAttrMapper, BaseSaleAttr> implements BaseSaleAttrService {

    @Autowired
    BaseSaleAttrMapper baseSaleAttrMapper;

    @Override
    public List<BaseSaleAttr> baseSaleAttrs() {
        List<BaseSaleAttr> saleAttrs = baseSaleAttrMapper.selectList(null);
        return saleAttrs;
    }
}
