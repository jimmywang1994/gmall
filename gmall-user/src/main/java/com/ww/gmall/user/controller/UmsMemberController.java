package com.ww.gmall.user.controller;


import com.ww.gmall.ums.bean.UmsMember;
import com.ww.gmall.ums.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public UmsMember login(@RequestParam("username") String userName,
                           @RequestParam("password") String passWord) {
        UmsMember member = umsMemberService.login(userName, passWord);
        return member;
    }

    @RequestMapping("addToken")
    public void addToken(@RequestParam("token") String token, @RequestParam("memberId") String memberId) {
        umsMemberService.addToken(token, memberId);
    }

    @RequestMapping("addOauthUser")
    public void addOauthUser(@RequestBody UmsMember umsMember) {
        umsMemberService.addOauthUser(umsMember);
    }

    @RequestMapping("checkOauthUser")
    public UmsMember checkOauthUser(@RequestParam("sourceUid") String sourceUid) {
        return umsMemberService.checkOauthUser(sourceUid);
    }

}
