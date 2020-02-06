package com.ww.gmall.cart.controller;


import com.ww.gmall.oms.bean.CartItem;
import com.ww.gmall.oms.service.CartItemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 购物车表 前端控制器
 * </p>
 *
 * @author wwei
 * @since 2020-02-05
 */
@RestController
@RequestMapping("/oms/cart-item")
public class CartItemController {
    @Autowired
    CartItemService cartItemService;

    @RequestMapping("/ifExistCartsByUser")
    public CartItem ifExistCartsByUser(@RequestParam("memberId") String memberId, @RequestParam("skuId") String skuId) {
        CartItem cartItem = new CartItem();
        cartItem = cartItemService.ifExistCartsByUser(memberId, skuId);
        return cartItem;
    }

    @RequestMapping("/addCart")
    public void addCart(@RequestBody CartItem cartItem) {
        if (StringUtils.isNotBlank(cartItem.getMemberId().toString())) {
            cartItemService.addCart(cartItem);
        }
    }

    @RequestMapping("/updateCart")
    public void updateCart(@RequestBody CartItem cartItemFromDb) {
        cartItemService.updateCart(cartItemFromDb);
    }

    @RequestMapping("/flushCartCache")
    public void flushCartCache(@RequestParam("memberId") String memberId) {
        cartItemService.flushCartCache(memberId);
    }

    @RequestMapping("/cartList")
    public List<CartItem> cartList(@RequestParam("memberId") String memberId) {
        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList = cartItemService.cartList(memberId);
        return cartItemList;
    }

    @RequestMapping("/checkCart")
    public void checkCart(@RequestBody CartItem cartItem) {
        cartItemService.checkCart(cartItem);
    }
}
