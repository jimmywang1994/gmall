package com.ww.gmall.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.pms.bean.ProductInfo;
import com.ww.gmall.pms.mapper.ProductInfoMapper;
import com.ww.gmall.pms.service.ProductInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements ProductInfoService {

}
