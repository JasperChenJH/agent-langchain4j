package com.cqupt.java.ai.langchain4j.tokenInterceptor;

import com.cqupt.java.ai.langchain4j.context.BaseContext;
import com.cqupt.java.ai.langchain4j.entity.User;
import com.cqupt.java.ai.langchain4j.jwt.JWTUtils;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Resource
    private User user;
    private static final Logger log = LoggerFactory.getLogger(TokenInterceptor.class);
    @Override
    // 在目标方法执行之前执行,Controller方法之前运行,返回为true则放行,false则拦截
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取请求头中的令牌
        String jwt = request.getHeader("token");
        //2.判断是否为空
        if(jwt==null || jwt.isEmpty()){
            log.info("请求头中没有token,未登录");
            response.setStatus(401);
            return false;
        }
        //3.解析令牌,判断是否合法
        try {
            log.info("解析令牌,判断是否合法");
            user.setToken(jwt);
            //JWTUtils.parse(user) 执行时 没有抛出异常
            // （如签名校验失败、Token 过期、格式错误等都会抛异常
            Claims claims = JWTUtils.parse(user);
            Object id = claims.get("id");// 能走到这一步，说明解析没抛异常
            BaseContext.setCurrentUserId(Long.parseLong(id.toString()));
        }catch (Exception e){
            log.info("解析令牌失败,令牌不合法");
            response.setStatus(401);
            return false;
        }
        log.info("令牌合法,放行");
        return true;
    }
}