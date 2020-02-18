package com.ww.gmall.payment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.oms.bean.PaymentInfo;
import com.ww.gmall.oms.service.PaymentInfoService;
import com.ww.gmall.payment.mapper.PaymentInfoMapper;
import org.springframework.stereotype.Service;

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
        baseMapper.update(paymentInfo, wrapper);
    }
}
