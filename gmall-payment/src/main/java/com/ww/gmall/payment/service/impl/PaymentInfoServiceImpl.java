package com.ww.gmall.payment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.oms.bean.PaymentInfo;
import com.ww.gmall.oms.service.PaymentInfoService;
import com.ww.gmall.payment.mapper.PaymentInfoMapper;
import com.ww.gmall.util.SendMessageUtil;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {
    @Override
    public void savePaymentInfo(PaymentInfo paymentInfo) {
        baseMapper.insert(paymentInfo);
    }

    @Override
    public void updatePaymentInfo(PaymentInfo paymentInfo) {
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("order_sn", paymentInfo.getOrderSn());
        SendMessageUtil messageUtil = new SendMessageUtil();
        try {
            baseMapper.update(paymentInfo, wrapper);
            //发送支付成功消息到订单服务
            messageUtil.sendMsg("PAYMENT_SUCCESS_QUEUE", paymentInfo, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void sendDelayPaymentResultCheckQueue(String outTradeNo) {
        SendMessageUtil messageUtil = new SendMessageUtil();
        //发送延迟队列
        messageUtil.sendMsg("PAYMENT_CHECK_QUEUE", null, outTradeNo);
    }
}
