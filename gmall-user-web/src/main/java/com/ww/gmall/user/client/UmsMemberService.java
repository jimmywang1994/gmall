package com.ww.gmall.user.client;

import com.ww.gmall.bean.UmsMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;
import java.util.List;

@FeignClient(value = "gmall-user")
public interface UmsMemberService {
    @RequestMapping("/user/ums-member/allMembers")
    public List<UmsMember> allMembers();
}
