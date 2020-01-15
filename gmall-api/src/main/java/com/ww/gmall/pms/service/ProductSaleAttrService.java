package com.ww.gmall.pms.service;

import com.ww.gmall.pms.bean.ProductImage;
import com.ww.gmall.pms.bean.ProductSaleAttr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
public interface ProductSaleAttrService extends IService<ProductSaleAttr> {
    List<ProductSaleAttr> productSaleAttrList(String productId);

    List<ProductImage> productImageList(String productId);
}
