package com.ww.gmall.cart.controller;

import ch.qos.logback.core.util.COWArrayList;
import com.alibaba.fastjson.JSON;
import com.ww.gmall.cart.client.CartClient;
import com.ww.gmall.cart.client.SkuClient;
import com.ww.gmall.oms.bean.CartItem;
import com.ww.gmall.pms.bean.SkuInfo;
import com.ww.gmall.util.CookieUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.awt.geom.AreaOp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    SkuClient skuClient;
    @Autowired
    CartClient cartClient;

    @RequestMapping("addToCart")
    public String addToCart(@RequestParam("skuId") String skuId, int quantity, HttpServletRequest request, HttpServletResponse response) {
        //调用商品服务查询商品信息
        SkuInfo skuInfo = skuClient.skuById(skuId, request.getRemoteAddr());
        List<CartItem> cartItemList = new ArrayList<>();
        //商品信息封装成购物车信息
        CartItem cartItem = new CartItem();
        cartItem.setCreateDate(new Date());
        cartItem.setDeleteStatus(0);
        cartItem.setModifyDate(new Date());
        cartItem.setPrice(skuInfo.getPrice());
        cartItem.setProductAttr("");
        cartItem.setProductBrand("");
        cartItem.setProductCategoryId(skuInfo.getCatalog3Id());
        cartItem.setProductId(Long.parseLong(skuInfo.getProductId()));
        cartItem.setProductName(skuInfo.getSkuName());
        cartItem.setProductPic(skuInfo.getSkuDefaultImg());
        cartItem.setProductSkuId(skuId);
        cartItem.setQuantity(quantity);
        //判断用户是否登录
        String memberId = "";
        if (StringUtils.isBlank(memberId)) {
            //用户未登录
            //购物车cookie
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            if (StringUtils.isBlank(cartListCookie)) {
                //cookie为空
                cartItemList.add(cartItem);
            } else {
                //cookie不为空
                cartItemList = JSON.parseArray(cartListCookie, CartItem.class);
                boolean exist = if_cart_exist(cartItemList, cartItem);
                if (exist) {
                    //之前添加过，更新购物车添加数量,并修改购物车金额数
                    for (CartItem item : cartItemList) {
                        if (item.getProductSkuId().equals(cartItem.getProductSkuId())) {
                            item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                            item.setPrice(item.getPrice().add(cartItem.getPrice()));
                        }
                    }
                } else {
                    //未添加过购物车，直接加到购物车cookie中
                    cartItemList.add(cartItem);
                }
            }
            CookieUtil.setCookie(request, response, "cartListCookie", JSON.toJSONString(cartItemList), 60 * 60 * 72, true);
        } else {
            //用户已登录
            //DB中查询购物车数据
            CartItem cartItemFromDb = cartClient.ifExistCartsByUser(memberId,skuId);
            if (cartItemFromDb == null) {
                //该用户没有添加过当前商品
                cartItem.setMemberId(Long.parseLong(memberId));
                cartClient.addCart(cartItem);
            } else {
                //该用户添加过当前商品
                cartItemFromDb.setQuantity(cartItem.getQuantity());
                cartClient.updateCart(cartItemFromDb);
            }
            //同步缓存
            cartClient.flushCartCache(memberId);
        }
        return "redirect:/success.html";
    }

    private boolean if_cart_exist(List<CartItem> cartItemList, CartItem cartItem) {
        boolean b = false;
        for (CartItem item : cartItemList) {
            if (item.getProductSkuId().equals(cartItem.getProductSkuId())) {
                b = true;
            }
        }
        return b;
    }

}
