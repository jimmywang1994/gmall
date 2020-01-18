package com.ww.gmall.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.pms.bean.ProductImage;
import com.ww.gmall.pms.bean.ProductSaleAttr;
import com.ww.gmall.pms.bean.ProductSaleAttrValue;
import com.ww.gmall.pms.mapper.ProductImageMapper;
import com.ww.gmall.pms.mapper.ProductSaleAttrMapper;
import com.ww.gmall.pms.mapper.ProductSaleAttrValueMapper;
import com.ww.gmall.pms.service.ProductSaleAttrService;
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
public class ProductSaleAttrServiceImpl extends ServiceImpl<ProductSaleAttrMapper, ProductSaleAttr> implements ProductSaleAttrService {

    @Autowired
    ProductSaleAttrMapper productSaleAttrMapper;
    @Autowired
    ProductSaleAttrValueMapper productSaleAttrValueMapper;
    @Autowired
    ProductImageMapper productImageMapper;

    @Override
    public List<ProductSaleAttr> productSaleAttrList(String productId) {
        QueryWrapper<ProductSaleAttr> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id", productId);
        List<ProductSaleAttr> productSaleAttrList = productSaleAttrMapper.selectList(wrapper);
        for (ProductSaleAttr saleAttr : productSaleAttrList) {
            QueryWrapper<ProductSaleAttrValue> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("product_id", saleAttr.getProductId());
            wrapper1.eq("sale_attr_id", saleAttr.getSaleAttrId());
            List<ProductSaleAttrValue> productSaleAttrValueList = productSaleAttrValueMapper.selectList(wrapper1);
            saleAttr.setSpuSaleAttrValueList(productSaleAttrValueList);
        }
        return productSaleAttrList;
    }

    @Override
    public List<ProductImage> productImageList(String productId) {
        QueryWrapper<ProductImage> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id", productId);
        List<ProductImage> productImageList = productImageMapper.selectList(wrapper);
        return productImageList;
    }

    @Override
    public List<ProductSaleAttr> productSaleAttrListCheckBySku(String productId, String skuId) {
        List<ProductSaleAttr> saleAttrList = productSaleAttrMapper.productSaleAttrListCheckBySku(productId, skuId);
        return saleAttrList;
    }
}
