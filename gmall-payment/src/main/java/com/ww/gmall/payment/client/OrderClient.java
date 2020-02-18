package com.ww.gmall.payment.client;

import com.ww.gmall.oms.bean.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Wwei
 */
@FeignClient("gmall-cart-service")
public interface OrderClient {
    @RequestMapping("/oms/order/getOrderByOutTradeNo")
    public Order getOrderByOutTradeNo(@RequestParam("outTradeNo") String outTradeNo);
}
