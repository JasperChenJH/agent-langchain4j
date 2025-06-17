package com.cqupt.java.ai.langchain4j.controller;

import com.cqupt.java.ai.langchain4j.entity.Result;
import com.cqupt.java.ai.langchain4j.entity.User;
import com.cqupt.java.ai.langchain4j.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "登录")
@RequestMapping("/login")
@RestController
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @Resource
    private UserService userService;
    @PostMapping
    public Result login(@RequestBody User user) {
        log.info("登录{}",user);
        User userInfo = userService.findByUsernameAndPassword(user);
        if (userInfo == null){
            return Result.error("用户名或密码错误!");
        }
        return Result.success(userInfo);
    }
}
