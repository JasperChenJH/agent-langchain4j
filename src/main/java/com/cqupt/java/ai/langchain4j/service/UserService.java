package com.cqupt.java.ai.langchain4j.service;

import com.cqupt.java.ai.langchain4j.entity.User;

public interface UserService {
    // 根据用户名和密码查询用户
    public User findByUsernameAndPassword(User user);
}
