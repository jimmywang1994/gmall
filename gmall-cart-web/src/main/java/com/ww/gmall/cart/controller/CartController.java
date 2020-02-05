package com.ww.gmall.cart.controller;

import ch.qos.logback.core.util.COWArrayList;
import com.alibaba.fastjson.JSON;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    SkuClient skuClient;

    @RequestMapping("addToCart")
    public String addToCart(@RequestParam("skuId") String skuId, int quantity, HttpServletRequest request, HttpServletResponse response) {
        //调用商品服务查询商品信息
        SkuInfo skuInfo = skuClient.skuById(skuId, request.getRemoteAddr());
        //商品信息封装成购物车信息
        CartItem cartItem=new CartItem();
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
            List<CartItem> cartItemList=new ArrayList<>();
            cartItemList.add(cartItem);
            //用户未登录
            CookieUtil.setCookie(request,response,"cartListCookie", JSON.toJSONString(cartItemList),60*60*72,true);
        } else {
            //用户已登录
        }
        return "redirect:/success.html";
    }

}
