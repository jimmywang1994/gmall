package com.ww.gmall.order.controller;

import com.ww.gmall.Constants.CommonConstant;
import com.ww.gmall.annotation.LoginRequired;
import com.ww.gmall.oms.bean.CartItem;
import com.ww.gmall.oms.bean.Order;
import com.ww.gmall.oms.bean.OrderItem;
import com.ww.gmall.order.client.CartClient;
import com.ww.gmall.order.client.SkuClient;
import com.ww.gmall.order.client.UserClient;
import com.ww.gmall.ums.bean.UmsMemberReceiveAddress;
import com.ww.gmall.util.AmountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    @Autowired
    SkuClient skuClient;

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
    public ModelAndView submitOrder(@RequestParam("receiveAddressId") String receiveAddressId, @RequestParam("totalAmount") BigDecimal totalAmount,
                                    @RequestParam("tradeCode") String tradeCode,
                                    HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        String memberId = (String) request.getAttribute("memberId");
        String nickname = (String) request.getAttribute("nickname");
        ModelAndView mv = new ModelAndView();
        //检查交易码
        String success = cartClient.checkTradeCode(memberId, tradeCode);
        //生成外部订单号
        String outTradeNo = "gmall";
        outTradeNo = outTradeNo + System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        outTradeNo = outTradeNo + sdf.format(new Date());
        if (success.equals(CommonConstant.SUCCESS)) {
            //根据用户id获得要购买的商品列表(购物车)
            List<OrderItem> orderItemList = new ArrayList<>();
            //订单对象
            Order order = new Order();
            order.setAutoConfirmDay(7);
            order.setCreateTime(new Date());
            order.setMemberId(Long.parseLong(memberId));
            order.setMemberUsername(nickname);
            //外部订单号
            order.setOrderSn(outTradeNo);
            order.setPayAmount(totalAmount);
            order.setOrderType(0);
            UmsMemberReceiveAddress umsMemberReceiveAddress = userClient.umsMemberReceiveAddress(receiveAddressId);
            order.setReceiverCity(umsMemberReceiveAddress.getCity());
            order.setReceiverName(umsMemberReceiveAddress.getName());
            order.setReceiverPhone(umsMemberReceiveAddress.getPhoneNumber());
            order.setReceiverPostCode(umsMemberReceiveAddress.getPostCode());
            order.setReceiverDetailAddress(umsMemberReceiveAddress.getDetailAddress());
            order.setReceiverProvince(umsMemberReceiveAddress.getProvince());
            order.setReceiverRegion(umsMemberReceiveAddress.getRegion());
            Calendar calendar = Calendar.getInstance();
            //当前日期加一天
            calendar.add(Calendar.DATE, 1);
            Date receiveDate = calendar.getTime();
            order.setReceiveTime(receiveDate);
            order.setSourceType(0);
            order.setStatus(0);
            order.setTotalAmount(totalAmount);
            //购物车列表
            List<CartItem> cartItemList = cartClient.cartList(memberId);
            for (CartItem cartItem : cartItemList) {
                if (cartItem.getIsChecked().equals(CommonConstant.TRUE)) {
                    //获得订单详情列表
                    OrderItem orderItem = new OrderItem();
                    //验价
                    boolean result = skuClient.checkPrice(cartItem.getProductSkuId(), cartItem.getPrice());
                    if (!result) {
                        mv.setViewName("tradeFail");
                        return mv;
                    }
                    orderItem.setProductPic(cartItem.getProductPic());
                    orderItem.setProductName(cartItem.getProductName());
                    //生成外部订单号，用来和其他系统交互，防止重复
                    orderItem.setOrderSn(outTradeNo);
                    orderItem.setProductCategoryId(cartItem.getProductCategoryId());
                    orderItem.setProductPrice(cartItem.getPrice());
                    orderItem.setRealAmount(cartItem.getTotalPrice());
                    orderItem.setProductQuantity(cartItem.getQuantity());
                    orderItem.setProductSkuId(Long.parseLong(cartItem.getProductSkuId()));
                    orderItem.setProductId(cartItem.getProductId().toString());

                    orderItemList.add(orderItem);
                }
            }
            order.setOrderItemList(orderItemList);
            //验库存
            //将订单和订单详情写入数据库
            cartClient.saveOrder(order);
            //删除购物车对应商品
            //重定向到支付系统
            mv.setViewName("redirect:http://payment.gmall.com:9070");
            mv.addObject("outTradeNo", outTradeNo);
            mv.addObject("totalAmount", totalAmount);
            return mv;
        } else {
            mv.setViewName("tradeFail");
            return mv;
        }
    }
}
