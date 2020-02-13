package com.ww.gmall.util;

import com.ww.gmall.oms.bean.CartItem;

import java.math.BigDecimal;
import java.util.List;

public class AmountUtil {
    public static BigDecimal getTotalAmount(List<CartItem> cartItemList){
        BigDecimal totalAmount = new BigDecimal("0");
        for (CartItem cartItem : cartItemList) {
            BigDecimal totalPrice = cartItem.getTotalPrice();
            if (cartItem.getIsChecked().equals("1")) {
                totalAmount = totalAmount.add(totalPrice);
            }
        }
        return totalAmount;
    }
}
