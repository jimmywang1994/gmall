package com.ww.gmall.passport.client;

import com.ww.gmall.ums.bean.UmsMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("gmall-user")
public interface UserClient {

    public UmsMember login(@RequestBody UmsMember umsMember);
}
