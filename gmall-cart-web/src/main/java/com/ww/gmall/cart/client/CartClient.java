package com.ww.gmall.cart.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("gmall-cart-service")
public interface CartClient {
}
