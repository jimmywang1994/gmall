package com.ww.gmall.pms.mapper;

import com.ww.gmall.pms.bean.ProductSaleAttr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
public interface ProductSaleAttrMapper extends BaseMapper<ProductSaleAttr> {
    List<ProductSaleAttr> productSaleAttrListCheckBySku(String productId,String skuId);
}
