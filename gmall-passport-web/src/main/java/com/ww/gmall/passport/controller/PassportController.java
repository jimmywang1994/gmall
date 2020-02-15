package com.ww.gmall.passport.controller;

import com.alibaba.fastjson.JSON;
import com.ww.gmall.Constants.CommonConstant;
import com.ww.gmall.passport.client.UserClient;
import com.ww.gmall.ums.bean.UmsMember;
import com.ww.gmall.util.HttpClientUtil;
import com.ww.gmall.util.JwtUtil;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.DigestUtils;
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
    public String index(@RequestParam(value = "ReturnUrl", required = false) String returnUrl, ModelMap modelMap) {
        modelMap.put("ReturnUrl", returnUrl);
        return "index";
    }

    @RequestMapping("vlogin")
    public String vlogin(@RequestParam("code") String code, HttpServletRequest request, ModelMap modelMap) {
        //通过获得的code向第三方平台换取access_token
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("client_id", CommonConstant.CLIENT_ID);
        paramMap.put("client_secret", CommonConstant.CLIENT_SECRET);
        paramMap.put("grant_type", "authorization_code");
        paramMap.put("code", code);
        paramMap.put("redirect_uri", CommonConstant.REDIRECT_URI);
        String accessTokenJson = HttpClientUtil.doPost("https://api.weibo.com/oauth2/access_token", paramMap);
        Map<String, String> tokenJson = new HashMap<>();
        tokenJson = JSON.parseObject(accessTokenJson, Map.class);
        String access_token = (String) tokenJson.get("access_token");
        String uid = (String) tokenJson.get("uid");
        //access_token换取用户信息
        //将用户信息存入数据库，用户来源设置为微博用户
        String userJson = HttpClientUtil.doGet("https://api.weibo.com/2/users/show.json?access_token=" + access_token + "&uid=" + uid);
        Map<String, String> userMap = JSON.parseObject(userJson, Map.class);
        UmsMember umsMember = new UmsMember();
        umsMember.setSourceType(2);
        umsMember.setAccessCode(code);
        umsMember.setAccessToken(access_token);
        umsMember.setNickname(userMap.get("screen_name"));
        umsMember.setSourceUid(userMap.get("idstr"));
        umsMember.setCity(userMap.get("location"));
        umsMember.setGender(userMap.get("gender"));
        UmsMember memberCheck = userClient.checkOauthUser(umsMember.getSourceUid());
        if (memberCheck == null) {
            umsMember = userClient.addOauthUser(umsMember);
        } else {
            umsMember = memberCheck;
        }
        //生成jwt的token,并重定向到index，携带token
        String token = "";
        String memberId = umsMember.getId().toString();
        String nickname = umsMember.getNickname();
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("nickname", nickname);
        String remoteAddr = request.getRemoteAddr();
        //通过nginx转发的客户端ip
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtil.isBlank(ip)) {
            ip = remoteAddr;
        }
        //按照设计的算法对参数进行加密后，生成token
        //公共key
        String key = DigestUtils.md5DigestAsHex(CommonConstant.ACCESS_KEY.getBytes());
        //盐值
        String salt = DigestUtils.md5DigestAsHex(ip.getBytes());
        token = JwtUtil.encode(key, map, salt);
        //将token存入redis
        userClient.addToken(token, memberId);
        return "redirect:http://search.gmall.com:8050/index?token=" + token;
    }

    /**
     * 登录方法
     * @param userName
     * @param passWord
     * @param request
     * @return
     */
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
            String key = DigestUtils.md5DigestAsHex(CommonConstant.ACCESS_KEY.getBytes());
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

    /**
     * 验证token真假
     * @param token
     * @param currentIp
     * @return
     */
    @RequestMapping("verify")
    @ResponseBody
    public String verify(@RequestParam("token") String token, String currentIp) {
        //通过jwt验证token真假
        Map<String, String> userMap = new HashMap<>();
        //按照设计的算法对参数进行加密后，生成token
        String key = DigestUtils.md5DigestAsHex(CommonConstant.ACCESS_KEY.getBytes());
        String salt = DigestUtils.md5DigestAsHex(currentIp.getBytes());
        Map<String, Object> decode = JwtUtil.decode(token, key, salt);
        if (decode == null) {
            userMap.put("status", CommonConstant.FAILED);
        } else {
            userMap.put("memberId", (String) decode.get("memberId"));
            userMap.put("nickname", (String) decode.get("nickname"));
            userMap.put("status", CommonConstant.SUCCESS);
        }
        return JSON.toJSONString(userMap);
    }
}
