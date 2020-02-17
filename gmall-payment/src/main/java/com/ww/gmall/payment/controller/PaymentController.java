package com.ww.gmall.payment.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.ww.gmall.Constants.CommonConstant;
import com.ww.gmall.annotation.LoginRequired;
import com.ww.gmall.payment.config.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PaymentController {
    @Autowired
    AlipayClient alipayClient;

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
        return "finish";
    }

    @RequestMapping("alipay/submit")
    @LoginRequired(loginSuccess = true)
    @ResponseBody
    public String alipay(@RequestParam("outTradeNo") String outTradeNo,
                         @RequestParam("totalAmount") String totalAmount,
                         HttpServletRequest request, ModelMap modelMap) {
        //获得一个支付宝请求的客户端
        String form = null;
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
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
        //提交请求到支付宝
        return form;
    }
}
