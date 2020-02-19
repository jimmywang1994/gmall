package com.ww.gmall.cart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.cart.mapper.OrderItemMapper;
import com.ww.gmall.cart.mapper.OrderMapper;
import com.ww.gmall.config.RedisUtil;
import com.ww.gmall.oms.bean.Order;
import com.ww.gmall.oms.bean.OrderItem;
import com.ww.gmall.oms.bean.PaymentInfo;
import com.ww.gmall.oms.service.OrderService;
import com.ww.gmall.util.ActiveMQUtil;
import com.ww.gmall.util.SendMessageUtil;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
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
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    ActiveMQUtil activeMQUtil;

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
                //检查通过后删除该交易码(lua脚本实现查到即删除),目的是防止高并发状态下的交易码重复使用
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

    @Override
    public void saveOrder(Order order) {
        //保存订单
        orderMapper.insert(order);
        String orderId = order.getId().toString();
        //保存订单详情
        List<OrderItem> orderItemList = order.getOrderItemList();
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderId(Long.parseLong(orderId));
            orderItemMapper.insert(orderItem);
            //删除购物车数据
        }
    }

    @Override
    public Order getOrderByOutTradeNo(String outTradeNo) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_sn", outTradeNo);
        Order order = orderMapper.selectOne(wrapper);
        return order;
    }

    @Override
    public void updateOrder(Order order) {
        SendMessageUtil messageUtil = new SendMessageUtil();
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_sn", order.getOrderSn());
        order.setStatus(1);
        orderMapper.update(order, wrapper);
        //发送一个订单已支付的消息，提供给库存消费
        messageUtil.sendMsg("ORDER_PAY_QUEUE", null, null);
    }
}
