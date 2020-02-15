package com.ww.gmall.order.client;

import com.ww.gmall.ums.bean.UmsMemberReceiveAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("gmall-user-service")
public interface UserClient {
    @RequestMapping("/user/ums-member-receive-address/allReceiveAddress")
    public List<UmsMemberReceiveAddress> allReceiveAddress(@RequestParam("memberId") String memberId);

    @RequestMapping("/user/ums-member-receive-address/getReceiveAddressById")
    public UmsMemberReceiveAddress umsMemberReceiveAddress(@RequestParam("receiveAddressId") String receiveAddressId);
}
