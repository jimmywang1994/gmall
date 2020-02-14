package com.ww.gmall.cart.controller;


import com.ww.gmall.oms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author wwei
 * @since 2020-02-05
 */
@RestController
@RequestMapping("/oms/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @RequestMapping("/genTradeCode")
    public String genTradeCode(@RequestParam("memberId") String memberId) {
        return null;
    }

    @RequestMapping("/checkTradeCode")
    public String checkTradeCode(@RequestParam("memberId") String memberId) {
        return null;
    }
}
