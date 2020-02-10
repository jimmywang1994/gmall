package com.ww.gmall.passport.controller;

import com.alibaba.fastjson.JSON;
import com.netflix.discovery.converters.Auto;
import com.ww.gmall.Contants.CommonContant;
import com.ww.gmall.passport.client.UserClient;
import com.ww.gmall.ums.bean.UmsMember;
import com.ww.gmall.util.JwtUtil;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PassportController {
    @Autowired
    UserClient userClient;

    @RequestMapping("index")
    public String index(@RequestParam("ReturnUrl") String returnUrl, ModelMap modelMap) {
        modelMap.put("ReturnUrl", returnUrl);
        return "index";
    }

    @RequestMapping("login")
    @ResponseBody
    public String login(@RequestParam("username") String userName,
                        @RequestParam("password") String passWord, HttpServletRequest request) {
        String token = "";
        //调用用户服务，验证用户名密码
        UmsMember member = userClient.login(userName, passWord);
        if (member != null) {
            //登录成功，用jwt制作token
            String memberId = member.getId().toString();
            String nickname = member.getNickname();
            //jwt私有部分(用户信息)
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("memberId", memberId);
            userMap.put("nickname", nickname);
            //主机的ip
            String remoteAddr = request.getRemoteAddr();
            //通过nginx转发的客户端ip
            String ip = request.getHeader("x-forwarded-for");
            if (StringUtil.isBlank(ip)) {
                ip = remoteAddr;
            }
            //按照设计的算法对参数进行加密后，生成token
            String key = DigestUtils.md5DigestAsHex(CommonContant.ACCESS_KEY.getBytes());
            String salt = DigestUtils.md5DigestAsHex(ip.getBytes());
            token = JwtUtil.encode(key, userMap, salt);
            //将token存入redis
            userClient.addToken(token, memberId);
        } else {
            //登录失败
            token = "fail";
        }
        return token;
    }

    @RequestMapping("verify")
    @ResponseBody
    public String verify(@RequestParam("token") String token,String currentIp) {
        //通过jwt验证token真假
        Map<String, String> userMap = new HashMap<>();
        //按照设计的算法对参数进行加密后，生成token
        String key = DigestUtils.md5DigestAsHex(CommonContant.ACCESS_KEY.getBytes());
        String salt = DigestUtils.md5DigestAsHex(currentIp.getBytes());
        Map<String, Object> decode = JwtUtil.decode(token, key, salt);
        if (decode == null) {
            userMap.put("status", CommonContant.FAILED);
        } else {
            userMap.put("memberId", (String) decode.get("memberId"));
            userMap.put("nickname", (String) decode.get("nickname"));
            userMap.put("status", CommonContant.SUCCESS);
        }
        return JSON.toJSONString(userMap);
    }
}
