package com.ww.gmall.order.client;

import com.ww.gmall.oms.bean.CartItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("gmall-cart-service")
public interface CartClient {
    @RequestMapping("/oms/cart-item/cartList")
    List<CartItem> cartList(@RequestParam("memberId") String memberId);
}
