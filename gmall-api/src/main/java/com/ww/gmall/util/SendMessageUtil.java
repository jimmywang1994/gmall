package com.ww.gmall.util;

import com.ww.gmall.oms.bean.PaymentInfo;
import jodd.util.StringUtil;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQMapMessage;

import javax.jms.*;

/**
 * 发送消息的工具类，用来和业务方法解耦
 */
public class SendMessageUtil {
    ActiveMQUtil activeMQUtil;
    Connection connection = null;
    Session session = null;

    public SendMessageUtil() {
        this.activeMQUtil = (ActiveMQUtil) ApplicationContextUtil.getBean(ActiveMQUtil.class);
    }

    /**
     * 发送消息的方法
     * @param queueName
     * @param paymentInfo
     * @param outTradeNo
     */
    public void sendMsg(String queueName, PaymentInfo paymentInfo,String outTradeNo) {
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
            //外部订单号
            if (paymentInfo != null) {
                mapMessage.setString("outTradeNo", paymentInfo.getOrderSn());
            }
            if(StringUtil.isNotBlank(outTradeNo)){
                mapMessage.setString("outTradeNo", outTradeNo);
                //延迟消息,为消息加入延迟时间
                mapMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY,1000*30);
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
