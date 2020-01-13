package com.ww.gmall.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.pms.bean.ProductInfo;
import com.ww.gmall.pms.mapper.ProductInfoMapper;
import com.ww.gmall.pms.service.ProductInfoService;
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
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements ProductInfoService {

    @Autowired
    ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> produceInfos(String catalog3Id) {
        QueryWrapper<ProductInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("catalog3_id", catalog3Id);
        return productInfoMapper.selectList(wrapper);
    }
}
