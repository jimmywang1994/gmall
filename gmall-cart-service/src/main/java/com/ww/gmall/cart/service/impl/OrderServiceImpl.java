package com.ww.gmall.cart.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.cart.mapper.OrderMapper;
import com.ww.gmall.config.RedisUtil;
import com.ww.gmall.oms.bean.Order;
import com.ww.gmall.oms.service.OrderService;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author wwei
 * @since 2020-02-05
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public String genTradeCode(String memberId) {
        String tradeCode = "";
        String tradeKey = "";
        /**
         * try with resource自动关闭资源
         */
        try (Jedis jedis = redisUtil.getJedis()) {
            tradeCode = UUID.randomUUID().toString();
            tradeKey = "user:" + memberId + ":tradeCode";
            jedis.setex(tradeKey, 60 * 15, tradeCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tradeCode;
    }

    @Override
    public String checkTradeCode(String memberId, String tradeCode) {
        String tradeKey = "";
        String tradeCodeFromCache = "";
        /**
         * try with resource自动关闭资源
         */
        try (Jedis jedis = redisUtil.getJedis()) {
            tradeKey = "user:" + memberId + ":tradeCode";
            tradeCodeFromCache = jedis.get(tradeKey);
            if (StringUtil.isNotBlank(tradeCodeFromCache) && tradeCodeFromCache.equals(tradeCode)) {
                //检查通过后删除该交易码(lua脚本实现查到即删除)
                //jedis.del(tradeKey);
                String script = "if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
                Long eval = (Long) jedis.eval(script, Collections.singletonList(tradeKey), Collections.singletonList(tradeCode));
                if (eval != null && eval != 0) {
                    return "success";
                } else {
                    return "fail";
                }
            } else {
                return "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
