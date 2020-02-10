package com.ww.gmall.user.controller;


import com.ww.gmall.ums.bean.UmsMember;
import com.ww.gmall.ums.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author wwei
 * @since 2020-01-09
 */
@RestController
@RequestMapping("/user/ums-member")
public class UmsMemberController {

    @Autowired
    UmsMemberService umsMemberService;

    @RequestMapping("allMembers")
    public List<UmsMember> allMembers() {
        List<UmsMember> umsMembers = umsMemberService.selectAllMember();
        return umsMembers;
    }

    @RequestMapping("login")
    public UmsMember login(@RequestBody UmsMember umsMember) {
        UmsMember member = umsMemberService.login(umsMember);
        return member;
    }

}
