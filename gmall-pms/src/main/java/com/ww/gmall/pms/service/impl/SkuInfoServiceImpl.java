package com.ww.gmall.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.pms.bean.SkuAttrValue;
import com.ww.gmall.pms.bean.SkuImage;
import com.ww.gmall.pms.bean.SkuInfo;
import com.ww.gmall.pms.bean.SkuSaleAttrValue;
import com.ww.gmall.pms.mapper.SkuAttrValueMapper;
import com.ww.gmall.pms.mapper.SkuImageMapper;
import com.ww.gmall.pms.mapper.SkuInfoMapper;
import com.ww.gmall.pms.mapper.SkuSaleAttrValueMapper;
import com.ww.gmall.pms.service.SkuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 库存单元表 服务实现类
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {

    @Autowired
    SkuInfoMapper skuInfoMapper;
    @Autowired
    SkuImageMapper skuImageMapper;
    @Autowired
    SkuAttrValueMapper skuAttrValueMapper;
    @Autowired
    SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Override
    public String saveSkuInfo(SkuInfo skuInfo) {
        skuInfoMapper.insert(skuInfo);
        if (skuInfo.getSkuAttrValueList().size() > 0) {
            List<SkuAttrValue> attrValueList = skuInfo.getSkuAttrValueList();
            for (SkuAttrValue attrValue : attrValueList) {
                attrValue.setSkuId(skuInfo.getId());
                skuAttrValueMapper.insert(attrValue);
            }
        }
        if (skuInfo.getSkuSaleAttrValueList().size() > 0) {
            List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
            for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
                skuSaleAttrValue.setSkuId(skuInfo.getId());
                skuSaleAttrValueMapper.insert(skuSaleAttrValue);
            }
        }
        if (skuInfo.getSkuImageList().size() > 0) {
            List<SkuImage> skuImageList = skuInfo.getSkuImageList();
            for (SkuImage skuImage : skuImageList) {
                skuImage.setSkuId(skuInfo.getId());
                skuImageMapper.insert(skuImage);
            }
        }
        return "success";
    }

    @Override
    public SkuInfo skuById(String id) {
        SkuInfo skuInfo = skuInfoMapper.selectById(id);
        return skuInfo;
    }

    @Override
    public List<SkuInfo> getSkuSaleAttrValueListBySku(String productId) {
        List<SkuInfo> skuInfoList=skuInfoMapper.getSkuSaleAttrValueListBySku(productId);
        return skuInfoList;
    }
}
