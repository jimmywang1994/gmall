package com.ww.gmall.oms.service;

import com.ww.gmall.oms.bean.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author wwei
 * @since 2020-02-05
 */
public interface OrderService extends IService<Order> {
    /**
     * 生成校验码
     *
     * @param memberId
     * @return
     */
    String genTradeCode(String memberId);

    /**
     * 检查校验码
     *
     * @param memberId
     * @param tradeCode
     * @return
     */
    String checkTradeCode(String memberId, String tradeCode);

    /**
     * 保存订单
     *
     * @param order
     */
    void saveOrder(Order order);

    Order getOrderByOutTradeNo(String outTradeNo);
}
