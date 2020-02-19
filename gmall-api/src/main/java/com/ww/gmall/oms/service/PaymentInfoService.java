package com.ww.gmall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ww.gmall.oms.bean.PaymentInfo;

public interface PaymentInfoService extends IService<PaymentInfo>{
    void savePaymentInfo(PaymentInfo paymentInfo);

    void updatePaymentInfo(PaymentInfo paymentInfo);

    void sendDelayPaymentResultCheckQueue(String outTradeNo);
}
