package com.ww.gmall.payment.controller;

import com.ww.gmall.annotation.LoginRequired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PaymentController {
    @RequestMapping("index")
    @LoginRequired(loginSuccess = true)
    public String index(@RequestParam("outTradeNo")String outTradeNo,
                        @RequestParam("totalAmount")String totalAmount,
                        HttpServletRequest request, ModelMap modelMap){
        String memberId=(String)request.getAttribute("memberId");
        String nickname=(String)request.getAttribute("nickname");
        modelMap.put("outTradeNo",outTradeNo);
        modelMap.put("totalAmount",totalAmount);
        return "index";
    }
}
