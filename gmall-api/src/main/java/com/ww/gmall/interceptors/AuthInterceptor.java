package com.ww.gmall.interceptors;

import com.ww.gmall.Contants.CommonContant;
import com.ww.gmall.annotation.LoginRequired;
import com.ww.gmall.util.CookieUtil;
import com.ww.gmall.util.HttpClientUtil;
import jodd.util.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import sun.net.www.http.HttpClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.MethodHandle;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //拦截代码
        //判断被拦截的请求的方法是否含有指定的注解
        HandlerMethod handle = (HandlerMethod) handler;
        LoginRequired methodAnnotation = handle.getMethodAnnotation(LoginRequired.class);
        //是否拦截
        if (methodAnnotation == null) {
            return true;
        }
        String token = "";
        //老token就是cookie里保存的token
        String oldToken = CookieUtil.getCookieValue(request, "oldToken", true);
        if (StringUtil.isNotBlank(oldToken)) {
            token = oldToken;
        }
        //新token是认证中心带的token
        String newToken = request.getParameter("token");
        if (StringUtil.isNotBlank(newToken)) {
            token = newToken;
        }
        //获得该请求是否必须登录成功
        boolean loginSuccess = methodAnnotation.loginSuccess();
        //调用认证中心验证
        String success="fail";
        if(StringUtil.isBlank(token)) {
            success = HttpClientUtil.doGet("http://passport.gmall.com:8020/verify?token=" + token);
        }
        //是否必须登录
        if (loginSuccess) {
            //必须登录成功才能使用
            if (!CommonContant.SUCCESS.equals(success)) {
                //重定向回passport登录
                //returnUrl:原始请求路径
                StringBuffer requestUrl = request.getRequestURL();
                response.sendRedirect("http://passport.gmall.com:8020/index?ReturnUrl=" + requestUrl);
                return false;
            } else {

                request.setAttribute("memberId", "");
                //验证通过，覆盖cookie中原有的token
                if(StringUtil.isNotBlank(token)) {
                    CookieUtil.setCookie(request, response, "oldToken", token, 60 * 2 * 60, true);
                }
            }
        } else {
            //可以不登录，但必须验证token
            if (CommonContant.SUCCESS.equals(success)) {
                //需要将token携带的用户信息写入cookie
                request.setAttribute("memberId", "");
                //验证通过，覆盖cookie中原有的token
                if(StringUtil.isNotBlank(token)) {
                    CookieUtil.setCookie(request, response, "oldToken", token, 60 * 2 * 60, true);
                }
            }
        }
        return true;
    }

}
