package com.ww.gmall.interceptors;

import com.ww.gmall.annotation.LoginRequired;
import com.ww.gmall.util.CookieUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

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
        if (methodAnnotation == null) {
            return true;
        }
        //获得该请求是否必须登录成功
        boolean loginSuccess = methodAnnotation.loginSuccess();
        return true;
    }

}
