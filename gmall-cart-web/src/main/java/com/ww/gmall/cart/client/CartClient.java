package com.ww.gmall.cart.client;

import com.ww.gmall.oms.bean.CartItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient("gmall-cart-service")
public interface CartClient {
    /**
     *
     * @param memberId
     * @param skuId
     * @return
     */
    @RequestMapping("/oms/cart-item/ifExistCartsByUser")
    CartItem ifExistCartsByUser(@RequestParam("memberId") String memberId, @RequestParam("skuId")String skuId);

    @RequestMapping("/oms/cart-item/addCart")
    void addCart(@RequestBody CartItem cartItem);

    @RequestMapping("/oms/cart-item/updateCart")
    void updateCart(@RequestBody CartItem cartItemFromDb);

    @RequestMapping("/oms/cart-item/flushCartCache")
    void flushCartCache(@RequestParam("memberId") String memberId);
}
