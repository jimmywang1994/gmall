package com.ww.gmall.pms.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.config.RedisUtil;
import com.ww.gmall.pms.bean.SkuAttrValue;
import com.ww.gmall.pms.bean.SkuImage;
import com.ww.gmall.pms.bean.SkuInfo;
import com.ww.gmall.pms.bean.SkuSaleAttrValue;
import com.ww.gmall.pms.mapper.SkuAttrValueMapper;
import com.ww.gmall.pms.mapper.SkuImageMapper;
import com.ww.gmall.pms.mapper.SkuInfoMapper;
import com.ww.gmall.pms.mapper.SkuSaleAttrValueMapper;
import com.ww.gmall.pms.service.SkuInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

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
    @Autowired
    RedisUtil redisUtil;

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

    public SkuInfo skuByIdFromDb(String id) {
        SkuInfo skuInfo = skuInfoMapper.selectById(id);
        return skuInfo;
    }

    @Override
    public SkuInfo skuById(String id) {
        SkuInfo skuInfo1 = new SkuInfo();
        //链接缓存
        Jedis jedis = redisUtil.getJedis();
        //查询缓存
        String skuKey = "sku:" + id + ":info";
        String skuJson = jedis.get(skuKey);
        if (StringUtils.isNotBlank(skuJson)) {
            skuInfo1 = JSON.parseObject(skuJson, SkuInfo.class);
        } else {
            //如果缓存中没有，再查库
            //加分布式锁
            String ok = jedis.set("sku:" + id + ":lock", "1", "nx", "px", 10);
            if (StringUtils.isNotBlank(ok) && ok.equals("OK")) {
                skuInfo1 = skuByIdFromDb(id);
            } else {
                //设置失败,自旋（该线程在睡眠几秒后，重新尝试访问）
                skuById(id);
            }
            if (skuInfo1 != null) {
                //查完库中的结果放入缓存
                jedis.set("sku:" + id + ":info", JSON.toJSONString(skuInfo1));
            }
        }
        jedis.close();
        return skuInfo1;
    }

    @Override
    public List<SkuInfo> getSkuSaleAttrValueListBySku(String productId) {
        List<SkuInfo> skuInfoList = skuInfoMapper.getSkuSaleAttrValueListBySku(productId);
        return skuInfoList;
    }
}
