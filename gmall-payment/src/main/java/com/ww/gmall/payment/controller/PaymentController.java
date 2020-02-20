package com.ww.gmall.payment.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.ww.gmall.Constants.CommonConstant;
import com.ww.gmall.annotation.LoginRequired;
import com.ww.gmall.oms.bean.Order;
import com.ww.gmall.oms.bean.PaymentInfo;
import com.ww.gmall.oms.service.OrderService;
import com.ww.gmall.oms.service.PaymentInfoService;
import com.ww.gmall.payment.client.OrderClient;
import com.ww.gmall.payment.config.AlipayConfig;
import com.ww.gmall.util.ActiveMQUtil;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PaymentController {
    @Autowired
    AlipayClient alipayClient;
    @Autowired
    PaymentInfoService paymentInfoService;
    @Autowired
    OrderClient orderClient;

    @RequestMapping("index")
    @LoginRequired(loginSuccess = true)
    public String index(@RequestParam("outTradeNo") String outTradeNo,
                        @RequestParam("totalAmount") String totalAmount,
                        HttpServletRequest request, ModelMap modelMap) {
        String memberId = (String) request.getAttribute("memberId");
        String nickname = (String) request.getAttribute("nickname");
        modelMap.put("outTradeNo", outTradeNo);
        modelMap.put("totalAmount", totalAmount);
        return "index";
    }

    @RequestMapping("mx/submit")
    @LoginRequired(loginSuccess = true)
    public String mx(@RequestParam("outTradeNo") String outTradeNo,
                     @RequestParam("totalAmount") String totalAmount,
                     HttpServletRequest request, ModelMap modelMap) {
        return "";
    }

    @RequestMapping("alipay/callback/return")
    @LoginRequired(loginSuccess = false)
    public String alipayCallbackReturn(HttpServletRequest request, ModelMap modelMap) {
        //更新用户支付状态
        //回调请求中获得支付宝参数
        String sign = request.getParameter("sign");
        String tradeNo = request.getParameter("trade_no");
        String outTradeNo = request.getParameter("out_trade_no");
        String tradeStatus = request.getParameter("trade_status");
        String totalAmount = request.getParameter("total_amount");
        String subject = request.getParameter("subject");
        String callback_content = request.getQueryString();
        if (StringUtil.isNotBlank(sign)) {
            //验签成功
            PaymentInfo paymentInfo = new PaymentInfo();
            paymentInfo.setOrderSn(outTradeNo);
            paymentInfo.setPaymentStatus("已支付");
            //支付宝的交易凭证号
            paymentInfo.setAlipayTradeNo(tradeNo);
            paymentInfo.setCallbackContent(callback_content);
            paymentInfo.setCallbackTime(new Date());
            paymentInfoService.updatePaymentInfo(paymentInfo);
        }
        //支付成功后，引起系统服务-》订单服务的更新—》库存服务-》物流服务
        //调用mq发送支付成功的消息

        return "finish";
    }

    @RequestMapping("alipay/submit")
    @LoginRequired(loginSuccess = true)
    @ResponseBody
    public String alipay(@RequestParam("outTradeNo") String outTradeNo,
                         @RequestParam("totalAmount") BigDecimal totalAmount,
                         HttpServletRequest request, ModelMap modelMap) {
        //获得一个支付宝请求的客户端
        String form = null;
        //创建API对应的request
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        Map<String, Object> map = new HashMap<>();
        map.put("out_trade_no", outTradeNo);
        map.put("product_code", CommonConstant.ALIPAY_PRODUCT_CODE);
        map.put("total_amount", 0.01);
        map.put("subject", "小米cc9");
        String param = JSON.toJSONString(map);
        alipayRequest.setBizContent(param);
        alipayRequest.setReturnUrl(AlipayConfig.return_payment_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_payment_url);
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody();
            System.out.println(form);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //生成并保存用户的支付状态
        Order order = orderClient.getOrderByOutTradeNo(outTradeNo);
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setCreateTime(new Date());
        paymentInfo.setOrderId(order.getId().toString());
        paymentInfo.setOrderSn(outTradeNo);
        paymentInfo.setPaymentStatus("未付款");
        paymentInfo.setSubject("gmall商城商品一件");
        paymentInfo.setTotalAmount(totalAmount);

        paymentInfoService.savePaymentInfo(paymentInfo);
        //向mq发送一个检查支付状态(支付服务消费)的延迟消息队列,发送次数限制为5次
        paymentInfoService.sendDelayPaymentResultCheckQueue(outTradeNo, 5);
        //提交请求到支付宝
        return form;
    }
}
