package com.ww.gmall.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.cart.mapper.CartItemMapper;
import com.ww.gmall.config.RedisUtil;
import com.ww.gmall.oms.bean.CartItem;
import com.ww.gmall.oms.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author wwei
 * @since 2020-02-05
 */
@Service
public class CartItemServiceImpl extends ServiceImpl<CartItemMapper, CartItem> implements CartItemService {
    @Autowired
    CartItemMapper cartItemMapper;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public CartItem ifExistCartsByUser(String memberId, String skuId) {
        QueryWrapper<CartItem> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id", memberId);
        wrapper.eq("product_sku_id", skuId);
        return cartItemMapper.selectOne(wrapper);
    }

    @Override
    public void addCart(CartItem cartItem) {
        cartItemMapper.insert(cartItem);
    }

    @Override
    public void updateCart(CartItem cartItemFromDb) {
        cartItemMapper.updateById(cartItemFromDb);
    }

    @Override
    public void flushCartCache(String memberId) {
        QueryWrapper<CartItem> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id", memberId);
        List<CartItem> cartItems = cartItemMapper.selectList(wrapper);
        //同步到redis缓存中
        Jedis jedis = redisUtil.getJedis();
        try {
            Map<String, String> map = new HashMap<>();
            for (CartItem item : cartItems) {
                map.put(item.getProductSkuId(), JSON.toJSONString(item));
            }
            jedis.hmset("user:" + memberId + ":cart", map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }
}
