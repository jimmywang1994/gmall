package com.ww.gmall.cart.mq;

import com.ww.gmall.oms.bean.Order;
import com.ww.gmall.oms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;

@Component
public class OrderServiceMqListener {
    @Autowired
    OrderService orderService;

    @JmsListener(destination = "PAYMENT_SUCCESS_QUEUE", containerFactory = "jmsQueueListener")
    public void consumePaymentResult(MapMessage mapMessage) throws JMSException {
        String outTradeNo = mapMessage.getString("outTradeNo");
        //更新订单状态业务
        System.out.println(outTradeNo);
        Order order = new Order();
        order.setOrderSn(outTradeNo);
        orderService.updateOrder(order);
    }
}
