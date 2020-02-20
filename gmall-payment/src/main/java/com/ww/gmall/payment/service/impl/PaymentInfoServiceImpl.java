package com.ww.gmall.payment.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.Constants.CommonConstant;
import com.ww.gmall.oms.bean.PaymentInfo;
import com.ww.gmall.oms.service.PaymentInfoService;
import com.ww.gmall.payment.config.AlipayConfig;
import com.ww.gmall.payment.mapper.PaymentInfoMapper;
import com.ww.gmall.util.SendMessageUtil;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {
    @Autowired
    AlipayClient alipayClient;

    @Override
    public void savePaymentInfo(PaymentInfo paymentInfo) {
        baseMapper.insert(paymentInfo);
    }

    @Override
    public void updatePaymentInfo(PaymentInfo paymentInfo) {
        //幂等性检查
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("order_sn", paymentInfo.getOrderSn());
        PaymentInfo paymentInfoResult = baseMapper.selectOne(wrapper);
        if (StringUtil.isNotBlank(paymentInfoResult.getPaymentStatus()) && paymentInfoResult.getPaymentStatus().equals(CommonConstant.IS_PAYED)) {
            return;
        }else{
            SendMessageUtil messageUtil = new SendMessageUtil();
            try {
                baseMapper.update(paymentInfo, wrapper);
                //发送支付成功消息到订单服务
                messageUtil.sendMsg("PAYMENT_SUCCESS_QUEUE", paymentInfo, null, -1);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void sendDelayPaymentResultCheckQueue(String outTradeNo, int count) {
        SendMessageUtil messageUtil = new SendMessageUtil();
        //发送延迟队列
        messageUtil.sendMsg("PAYMENT_CHECK_QUEUE", null, outTradeNo, count);
    }

    @Override
    public Map<String, Object> checkAlipayPayment(String outTradeNo) {
        Map<String, Object> resultMap = new HashMap<>();
        //创建API对应的request
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
        Map<String, Object> map = new HashMap<>();
        map.put("out_trade_no", outTradeNo);
        String param = JSON.toJSONString(map);
        alipayRequest.setBizContent(param);
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(alipayRequest);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                resultMap.put("outTradeNo", response.getOutTradeNo());
                resultMap.put("tradeStatus", response.getTradeStatus());
                //支付宝交易号
                resultMap.put("tradeNo", response.getTradeNo());
                resultMap.put("callBackContent", response.getMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return resultMap;
    }
}
