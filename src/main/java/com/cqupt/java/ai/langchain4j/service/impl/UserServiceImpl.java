package com.cqupt.java.ai.langchain4j.service.impl;

import com.cqupt.java.ai.langchain4j.entity.User;
import com.cqupt.java.ai.langchain4j.jwt.JWTUtils;
import com.cqupt.java.ai.langchain4j.mapper.UserMapper;
import com.cqupt.java.ai.langchain4j.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public User findByUsernameAndPassword(User user) {
        User userInfo = userMapper.findByUsernameAndPassword(user);
        if (userInfo != null){
            String jwtToken = JWTUtils.createJWTToken(userInfo);
            userInfo.setToken(jwtToken);
            return userInfo;
        }
        return null;
    }
}
