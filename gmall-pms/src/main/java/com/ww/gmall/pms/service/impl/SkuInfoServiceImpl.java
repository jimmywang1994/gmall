package com.ww.gmall.pms.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.config.RedisUtil;
import com.ww.gmall.pms.bean.*;
import com.ww.gmall.pms.mapper.SkuAttrValueMapper;
import com.ww.gmall.pms.mapper.SkuImageMapper;
import com.ww.gmall.pms.mapper.SkuInfoMapper;
import com.ww.gmall.pms.mapper.SkuSaleAttrValueMapper;
import com.ww.gmall.pms.service.SkuInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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
        System.out.println(Thread.currentThread().getName() + "进入数据库查询");
        return skuInfo;
    }

    @Override
    public SkuInfo skuById(String id, String ip) {
        System.out.println("ip为" + ip + "的用户:" + Thread.currentThread().getName() + "进入了商品详情");
        SkuInfo skuInfo1 = new SkuInfo();
        //链接缓存
        Jedis jedis = redisUtil.getJedis();
        //查询缓存
        String skuKey = "sku:" + id + ":info";
        String skuJson = jedis.get(skuKey);
        if (StringUtils.isNotBlank(skuJson)) {
            System.out.println("ip为" + ip + "的用户:" + Thread.currentThread().getName() + "从缓存中获取了商品详情");
            skuInfo1 = JSON.parseObject(skuJson, SkuInfo.class);
        } else {
            //如果缓存中没有，再查库
            System.out.println("ip为" + ip + "的用户:" + Thread.currentThread().getName() + "发现缓存中没有，申请缓存分布式锁" + "sku:" + id + ":lock");
            //加分布式锁
            String token = UUID.randomUUID().toString();

            String ok = jedis.set("sku:" + id + ":lock", token, "nx", "px", 10000);
            if (StringUtils.isNotBlank(ok) && ok.equals("OK")) {
                System.out.println("ip为" + ip + "的用户:" + Thread.currentThread().getName() + "有权在10秒之内访问数据库");
                //设置成功，有权在10秒内访问数据库
                skuInfo1 = skuByIdFromDb(id);
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                if (skuInfo1 != null) {
                    //查完库中的结果放入缓存
                    jedis.set("sku:" + id + ":info", JSON.toJSONString(skuInfo1));
                } else {
                    //数据库不存在sku，为了防止缓存穿透，将null或空字符串值设置给redis
                    jedis.setex("sku:" + id + ":info", 10, "");
                }
                System.out.println("ip为" + ip + "的用户:" + Thread.currentThread().getName() + "用完锁，将锁归还" + "sku:" + id + ":lock");
                String lockToken = jedis.get("sku:" + id + ":lock");
                if (StringUtils.isNotBlank(lockToken) && lockToken.equals(token)) {
                    //将锁释放,通过token确认删除的是自己线程的锁
                    String script = "if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
                    //根据token拿到锁的同时删除锁
                    jedis.eval(script, Collections.singletonList("sku:" + id + ":lock"), Collections.singletonList(lockToken));
                    //jedis.del("sku:" + id + ":lock");
                }
            } else {
                //设置失败,自旋（该线程在睡眠几秒后，重新尝试访问）
                System.out.println("ip为" + ip + "的用户:" + Thread.currentThread().getName() + "没有拿到锁，开始自旋");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return skuById(id, ip);
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

    @Override
    public List<SkuInfo> getAllSku(String catalog3Id) {
        List<SkuInfo> allSkuInfo = skuInfoMapper.selectList(null);
        for (SkuInfo skuInfo : allSkuInfo) {
            String skuId = skuInfo.getId().toString();
            QueryWrapper<SkuAttrValue> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("sku_id", skuId);
            List<SkuAttrValue> attrValueList = skuAttrValueMapper.selectList(wrapper2);
            skuInfo.setSkuAttrValueList(attrValueList);
        }
        return allSkuInfo;
    }

    @Override
    public boolean checkPrice(String skuId, BigDecimal price) {
        boolean result = false;
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        BigDecimal skuInfoPrice = skuInfo.getPrice();
        if (price.compareTo(skuInfoPrice) == 0) {
            result = true;
        }
        return result;
    }
}
