package com.ww.gmall.order.controller;

import com.ww.gmall.Contants.CommonContant;
import com.ww.gmall.annotation.LoginRequired;
import com.ww.gmall.oms.bean.CartItem;
import com.ww.gmall.oms.bean.OrderItem;
import com.ww.gmall.order.client.CartClient;
import com.ww.gmall.order.client.UserClient;
import com.ww.gmall.ums.bean.UmsMemberReceiveAddress;
import com.ww.gmall.util.AmountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
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
            if (cartItem.getIsChecked().equals("1")) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProductName(cartItem.getProductName());
                orderItem.setProductPic(cartItem.getProductPic());
                orderItemList.add(orderItem);
            }
        }
        modelMap.put("orderItemList", orderItemList);
        //收件人列表
        List<UmsMemberReceiveAddress> umsMemberReceiveAddressList = userClient.allReceiveAddress(memberId);
        modelMap.put("userAddressList", umsMemberReceiveAddressList);
        BigDecimal totalAmount = AmountUtil.getTotalAmount(cartItemList);
        modelMap.put("totalAmount", totalAmount);
        //生成交易码
        String tradeCode = cartClient.genTradeCode(memberId);
        modelMap.put("tradeCode", tradeCode);
        return "trade";
    }

    /**
     * 提交订单
     *
     * @param receiveAddressId
     * @param totalAmount
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("submitOrder")
    @LoginRequired(loginSuccess = true)
    public String submitOrder(@RequestParam("receiveAddressId") String receiveAddressId, @RequestParam("totalAmount") BigDecimal totalAmount,
                              @RequestParam("tradeCode") String tradeCode,
                              HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        String memberId = (String) request.getAttribute("memberId");
        //检查交易码
        String success = cartClient.checkTradeCode(memberId,tradeCode);
        if(success.equals(CommonContant.SUCCESS)){
            //根据用户id获得要购买的商品列表(购物车)
            //将订单和订单详情写入数据库
            //删除购物车对应商品
            //重定向到支付系统
        }
        else{
            return "tradeFail";
        }
        return null;
    }
}
