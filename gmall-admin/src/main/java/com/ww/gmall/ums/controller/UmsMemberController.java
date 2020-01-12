package com.ww.gmall.ums.controller;


import com.ww.gmall.ums.bean.UmsMember;
import com.ww.gmall.ums.client.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping("/allMember")
    public List<UmsMember> allMember(){
        return umsMemberService.allMembers();
    }

}
