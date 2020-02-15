package com.ww.gmall.user.controller;


import com.ww.gmall.ums.bean.UmsMemberReceiveAddress;
import com.ww.gmall.ums.service.UmsMemberReceiveAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 会员收货地址表 前端控制器
 * </p>
 *
 * @author wwei
 * @since 2020-01-09
 */
@RestController
@RequestMapping("/user/ums-member-receive-address")
public class UmsMemberReceiveAddressController {

    @Autowired
    UmsMemberReceiveAddressService umsMemberReceiveAddressService;

    @RequestMapping("allReceiveAddress")
    public List<UmsMemberReceiveAddress> allReceiveAddress(@RequestParam("memberId") String memberId) {
        List<UmsMemberReceiveAddress> umsMemberReceiveAddressList = umsMemberReceiveAddressService.umsMemberReceiveAddressList(memberId);
        return umsMemberReceiveAddressList;
    }

    @RequestMapping("getReceiveAddressById")
    public UmsMemberReceiveAddress umsMemberReceiveAddress(@RequestParam("receiveAddressId") String receiveAddressId) {
        return umsMemberReceiveAddressService.umsMemberReceiveAddress(receiveAddressId);
    }
}
