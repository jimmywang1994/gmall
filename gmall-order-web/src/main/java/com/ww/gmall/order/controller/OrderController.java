package com.ww.gmall.order.controller;

import com.ww.gmall.annotation.LoginRequired;
import com.ww.gmall.oms.bean.CartItem;
import com.ww.gmall.oms.bean.OrderItem;
import com.ww.gmall.order.client.CartClient;
import com.ww.gmall.order.client.UserClient;
import com.ww.gmall.ums.bean.UmsMemberReceiveAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单
 *
 * @author Wwei
 */
@Controller
public class OrderController {

    @Autowired
    CartClient cartClient;
    @Autowired
    UserClient userClient;

    /**
     * 跳转结算页
     *
     * @return
     */
    @RequestMapping("toTrade")
    @LoginRequired(loginSuccess = true)
    public String toTrade(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        String memberId = (String) request.getAttribute("memberId");
        String nickname = (String) request.getAttribute("nickname");
        List<CartItem> cartItemList = cartClient.cartList(memberId);
        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartItem cartItem : cartItemList) {
            if(cartItem.getIsChecked().equals("1")) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProductName(cartItem.getProductName());
                orderItem.setProductPic(cartItem.getProductPic());
                orderItemList.add(orderItem);
            }
        }
        modelMap.put("orderItemList",orderItemList);
        //收件人列表
        List<UmsMemberReceiveAddress> umsMemberReceiveAddressList = userClient.allReceiveAddress(memberId);
        modelMap.put("userAddressList", umsMemberReceiveAddressList);
        return "trade";
    }

}
