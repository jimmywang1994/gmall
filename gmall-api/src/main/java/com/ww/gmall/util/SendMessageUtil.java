package com.ww.gmall.util;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ww.gmall.oms.bean.Order;
import com.ww.gmall.oms.bean.OrderItem;
import com.ww.gmall.oms.bean.PaymentInfo;
import com.ww.gmall.oms.service.OrderItemService;
import com.ww.gmall.oms.service.OrderService;
import jodd.util.StringUtil;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;
import java.util.List;

/**
 * 发送消息的工具类，用来和业务方法解耦
 */
public class SendMessageUtil {
    ActiveMQUtil activeMQUtil;
    Connection connection = null;
    Session session = null;
    OrderService orderService;
    OrderItemService orderItemService;

    public SendMessageUtil() {
        this.activeMQUtil = (ActiveMQUtil) ApplicationContextUtil.getBean(ActiveMQUtil.class);
        this.orderService = (OrderService) ApplicationContextUtil.getBean(OrderService.class);
        this.orderItemService = (OrderItemService) ApplicationContextUtil.getBean(OrderItemService.class);
    }

    /**
     * 发送消息的方法
     *
     * @param queueName
     * @param paymentInfo
     * @param outTradeNo
     */
    public void sendMsg(String queueName, PaymentInfo paymentInfo, String outTradeNo, int count) {
        //消息队列
        try {
            connection = activeMQUtil.getConnectionFactory().createConnection();
            //消息队列的事务(开启事务)
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        //支付成功消息
        Queue payment_success_queue = null;
        try {
            payment_success_queue = session.createQueue(queueName);
            MessageProducer messageProducer = session.createProducer(payment_success_queue);
            //hash结构的消息
            MapMessage mapMessage = new ActiveMQMapMessage();
            TextMessage textMessage = new ActiveMQTextMessage();
            //外部订单号
            if (paymentInfo != null) {
                mapMessage.setString("outTradeNo", paymentInfo.getOrderSn());
            }
            if (count >= 0) {
                mapMessage.setInt("count", count);
            }
            if (StringUtil.isNotBlank(outTradeNo)) {
                mapMessage.setString("outTradeNo", outTradeNo);
                //延迟消息,为消息加入延迟时间
                mapMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 1000 * 30);
            }
            if (paymentInfo == null && StringUtil.isNotBlank(outTradeNo) && count < 0) {
                QueryWrapper<Order> wrapper = new QueryWrapper<>();
                wrapper.eq("order_sn", outTradeNo);
                QueryWrapper<OrderItem> itemWrapper = new QueryWrapper<>();
                itemWrapper.eq("order_sn", outTradeNo);
                List<OrderItem> orderItemList = orderItemService.list(itemWrapper);
                Order order = orderService.getOne(wrapper);
                order.setOrderItemList(orderItemList);
                textMessage.setText(JSON.toJSONString(order));
            }
            messageProducer.send(mapMessage);
            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
            try {
                session.rollback();
            } catch (JMSException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
