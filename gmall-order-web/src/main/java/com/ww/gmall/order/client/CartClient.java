package com.ww.gmall.order.client;

import com.ww.gmall.oms.bean.CartItem;
import com.ww.gmall.oms.bean.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@FeignClient("gmall-cart-service")
public interface CartClient {
    @RequestMapping("/oms/cart-item/cartList")
    List<CartItem> cartList(@RequestParam("memberId") String memberId);

    @RequestMapping("/oms/order/genTradeCode")
    String genTradeCode(@RequestParam("memberId")String memberId);

    @RequestMapping("/oms/order/checkTradeCode")
    String checkTradeCode(@RequestParam("memberId")String memberId,@RequestParam("tradeCode")String tradeCode);

    @RequestMapping("/oms/order/saveOrder")
    void saveOrder(@RequestBody Order order);
}
