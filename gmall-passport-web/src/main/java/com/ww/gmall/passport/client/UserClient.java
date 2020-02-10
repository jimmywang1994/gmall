package com.ww.gmall.passport.client;

import com.ww.gmall.ums.bean.UmsMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("gmall-user-service")
public interface UserClient {
    /**
     * 用户登录
     * @param userName
     * @param passWord
     * @return
     */
    @RequestMapping("/user/ums-member/login")
    public UmsMember login(@RequestParam("username")String userName,
                           @RequestParam("password")String passWord);

    /**
     * 将token存入redis
     * @param token
     * @param memberId
     */
    @RequestMapping("/user/ums-member/addToken")
    void addToken(@RequestParam("token") String token, @RequestParam("memberId") String memberId);
}
