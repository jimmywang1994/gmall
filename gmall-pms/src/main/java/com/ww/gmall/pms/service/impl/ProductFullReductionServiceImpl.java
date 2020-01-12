package com.ww.gmall.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.pms.bean.ProductFullReduction;
import com.ww.gmall.pms.mapper.ProductFullReductionMapper;
import com.ww.gmall.pms.service.ProductFullReductionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品满减表(只针对同商品) 服务实现类
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@Service
public class ProductFullReductionServiceImpl extends ServiceImpl<ProductFullReductionMapper, ProductFullReduction> implements ProductFullReductionService {

}
