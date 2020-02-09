package com.ww.gmall.passport.controller;

import com.ww.gmall.ums.bean.UmsMember;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PassportController {
    @RequestMapping("index")
    public String index(String ReturnUrl, ModelMap modelMap){
        modelMap.put("ReturnUrl",ReturnUrl);
        return "index";
    }

    @RequestMapping("login")
    @ResponseBody
    public String login(@RequestBody UmsMember umsMember){
        //调用用户服务，验证用户名密码

        return "token";
    }
}