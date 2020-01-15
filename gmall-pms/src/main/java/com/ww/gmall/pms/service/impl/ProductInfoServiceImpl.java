package com.ww.gmall.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.pms.bean.ProductImage;
import com.ww.gmall.pms.bean.ProductInfo;
import com.ww.gmall.pms.bean.ProductSaleAttr;
import com.ww.gmall.pms.bean.ProductSaleAttrValue;
import com.ww.gmall.pms.mapper.ProductImageMapper;
import com.ww.gmall.pms.mapper.ProductInfoMapper;
import com.ww.gmall.pms.mapper.ProductSaleAttrMapper;
import com.ww.gmall.pms.mapper.ProductSaleAttrValueMapper;
import com.ww.gmall.pms.service.ProductInfoService;
import com.ww.gmall.pms.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    ProductSaleAttrMapper productSaleAttrMapper;
    @Autowired
    ProductSaleAttrValueMapper productSaleAttrValueMapper;
    @Autowired
    ProductImageMapper productImageMapper;

    @Override
    public List<ProductInfo> produceInfos(String catalog3Id) {
        QueryWrapper<ProductInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("catalog3_id", catalog3Id);
        return productInfoMapper.selectList(wrapper);
    }

    @Override
    public String saveSpuInfo(ProductInfo productInfo) {
        productInfoMapper.insert(productInfo);
        long product_id = productInfo.getId();
        for (ProductSaleAttr saleAttr : productInfo.getSpuSaleAttrList()) {
            saleAttr.setProductId(product_id);
            productSaleAttrMapper.insert(saleAttr);
            for (ProductSaleAttrValue saleAttrValue : saleAttr.getSpuSaleAttrValueList()) {
                saleAttrValue.setProductId(product_id);
                saleAttrValue.setSaleAttrId(saleAttr.getSaleAttrId());
                productSaleAttrValueMapper.insert(saleAttrValue);
            }
        }
        for (ProductImage image : productInfo.getSpuImageList()) {
            image.setProductId(product_id);
            productImageMapper.insert(image);
        }
        return "success";
    }

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        UploadUtil.uploadUtil(multipartFile);
        return "success";
    }
}
