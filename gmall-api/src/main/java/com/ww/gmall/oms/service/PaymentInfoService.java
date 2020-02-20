package com.ww.gmall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ww.gmall.oms.bean.PaymentInfo;

import java.util.Map;

public interface PaymentInfoService extends IService<PaymentInfo>{
    void savePaymentInfo(PaymentInfo paymentInfo);

    void updatePaymentInfo(PaymentInfo paymentInfo);

    void sendDelayPaymentResultCheckQueue(String outTradeNo,int count);

    /**
     * 检查支付宝支付状态
     * @param outTradeNo
     * @return
     */
    Map<String, Object> checkAlipayPayment(String outTradeNo);
}
