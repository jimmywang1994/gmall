package com.ww.gmall.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.cart.mapper.CartItemMapper;
import com.ww.gmall.config.RedisUtil;
import com.ww.gmall.oms.bean.CartItem;
import com.ww.gmall.oms.service.CartItemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.*;

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
        try (Jedis jedis = redisUtil.getJedis()) {
            Map<String, String> map = new HashMap<>();
            for (CartItem item : cartItems) {
                item.setTotalPrice(item.getPrice().multiply(item.getQuantity()));
                map.put(item.getProductSkuId(), JSON.toJSONString(item));
            }
            jedis.del("user:" + memberId + ":cart");
            jedis.hmset("user:" + memberId + ":cart", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CartItem> cartList(String memberId) {
        List<CartItem> cartItemList = new ArrayList<>();
        try (Jedis jedis = redisUtil.getJedis()) {
            List<String> hvals = jedis.hvals("user:" + memberId + ":cart");
            if (hvals != null) {
                for (String hval : hvals) {
                    CartItem cartItem = JSON.parseObject(hval, CartItem.class);
                    cartItemList.add(cartItem);
                }
            } else {
                //如果缓存中没有，查数据库
                //加分布式锁
                String token = UUID.randomUUID().toString();
                String ok = jedis.set("user:" + memberId + ":lock", token, "nx", "px", 10000);
                if (StringUtils.isNotBlank(ok) && ok.equals("OK")) {
                    cartItemList = cartListFromDB(memberId);
                    if (cartItemList != null) {
                        Map<String, String> map = new HashMap<>();
                        for (CartItem item : cartItemList) {
                            map.put(item.getProductSkuId(), JSON.toJSONString(item));
                        }
                        //查完库中的结果放入缓存
                        jedis.hmset("user:" + memberId + ":cart", map);
                    } else {
                        //数据库不存在sku，为了防止缓存穿透，将null或空字符串值设置给redis
                        jedis.setex("user:" + memberId + ":info", 10, "");
                    }
                    String lockToken = jedis.get("user:" + memberId + ":lock");
                    if (StringUtils.isNotBlank(lockToken) && lockToken.equals(token)) {
                        //将锁释放,通过token确认删除的是自己线程的锁
                        String script = "if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
                        //根据token拿到锁的同时删除锁
                        jedis.eval(script, Collections.singletonList("user:" + memberId + ":lock"), Collections.singletonList(lockToken));
                        //jedis.del("sku:" + id + ":lock");
                    }
                } else {
                    //设置失败,自旋（该线程在睡眠几秒后，重新尝试访问）
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return cartList(memberId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return cartItemList;
    }

    @Override
    public void checkCart(CartItem cartItem) {
        QueryWrapper<CartItem> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id", cartItem.getMemberId());
        wrapper.eq("product_sku_id", cartItem.getProductSkuId());
        cartItemMapper.update(cartItem, wrapper);
        flushCartCache(cartItem.getMemberId().toString());
    }

    private List<CartItem> cartListFromDB(String memberId) {
        List<CartItem> cartItemList = new ArrayList<>();
        QueryWrapper<CartItem> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id", memberId);
        wrapper.orderByDesc("create_date");
        cartItemList = cartItemMapper.selectList(wrapper);
        return cartItemList;
    }
}
