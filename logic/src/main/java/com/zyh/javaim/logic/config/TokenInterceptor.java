package com.zyh.javaim.logic.config;

import com.zyh.javaim.logic.common.context.Context;
import com.zyh.javaim.logic.common.context.UserDetail;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


import static com.zyh.javaim.logic.common.jwt.JwtGenerator.parseJwtToken;


@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求中获取 Token
        String token = request.getHeader("Authorization");
        try {
            UserDetail userDetail = parseJwtToken(token);
            Context.setRequestId(userDetail);
        } catch  (Exception e) {
            log.error("TokenInterceptor preHandle" + e);
            return false;
        }

        return true;
    }

}