package com.ww.gmall.oms.service;

import com.ww.gmall.oms.bean.CartItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 购物车表 服务类
 * </p>
 *
 * @author wwei
 * @since 2020-02-05
 */
public interface CartItemService extends IService<CartItem> {
    /**
     * 判断当前用户是否有购物车数据
     * @param memberId
     * @param skuId
     * @return
     */
    CartItem ifExistCartsByUser(String memberId,String skuId);

    void addCart(CartItem cartItem);

    void updateCart(CartItem cartItemFromDb);

    void flushCartCache(String memberId);

    List<CartItem> cartList(String memberId);

    void checkCart(CartItem cartItem);
}
