package com.ww.gmall.payment.mq;

import com.alipay.api.AlipayClient;
import com.ww.gmall.Constants.CommonConstant;
import com.ww.gmall.oms.bean.PaymentInfo;
import com.ww.gmall.oms.service.PaymentInfoService;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import java.util.Date;
import java.util.Map;

@Component
/**
 * 检查支付宝支付状态的消息消费者
 */
public class PaymentServiceMqListener {
    @Autowired
    PaymentInfoService paymentInfoService;

    @JmsListener(destination = "PAYMENT_CHECK_QUEUE", containerFactory = "jmsQueueListener")
    public void consumePaymentCheckResult(MapMessage mapMessage) throws JMSException {
        String outTradeNo = mapMessage.getString("outTradeNo");
        int count = mapMessage.getInt("count");
        //调用paymentService的支付宝检查接口
        Map<String, Object> resultMap = paymentInfoService.checkAlipayPayment(outTradeNo);
        if (resultMap != null && !resultMap.isEmpty()) {
            //继续发起延迟检查任务，计算延迟时间等
            String tradeStatus = (String) resultMap.get("tradeStatus");
            //根据查询的交易状态，判断是否进行下一次延迟任务还是成功后更新数据
            if (StringUtil.isNotBlank(tradeStatus) && tradeStatus.equals(CommonConstant.TRADE_SUCCESS)) {
                //支付成功，更新支付发送支付队列
                //进行支付更新的幂等性检查
                boolean result = false;
                PaymentInfo paymentInfo = new PaymentInfo();
                paymentInfo.setOrderSn(outTradeNo);
                paymentInfo.setPaymentStatus("已支付");
                //支付宝的交易凭证号
                paymentInfo.setAlipayTradeNo((String) resultMap.get("tradeNo"));
                paymentInfo.setCallbackContent((String) resultMap.get("callBackContent"));
                paymentInfo.setCallbackTime(new Date());
                paymentInfoService.updatePaymentInfo(paymentInfo);
                System.out.println("已支付成功");
                return;
            }
        }
        if (count > 0) {
            System.out.println("未支付成功，剩余次数为" + count + ",继续发送延迟任务");
            count--;
            paymentInfoService.sendDelayPaymentResultCheckQueue(outTradeNo, count);
        } else {
            System.out.println("未支付成功，检查次数用尽");
        }
    }
}
