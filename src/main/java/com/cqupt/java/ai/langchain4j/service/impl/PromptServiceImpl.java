package com.cqupt.java.ai.langchain4j.service.impl;

import com.cqupt.java.ai.langchain4j.entity.User;
import com.cqupt.java.ai.langchain4j.service.PromptService;
import com.cqupt.java.ai.langchain4j.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PromptServiceImpl implements PromptService {
    @Resource
    private UserService userService;

    // 使用预定义的日期格式化器
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String generatePrompt(String userMessage) {
        User user = userService.findByUserId();
        LocalDate birthDate = user.getBirthDate();
        LocalDate today = LocalDate.now();

        String prompt =
                "用户名：" + user.getUsername() + "\n" +
                        "昵称：" + user.getNickname() + "\n" +
                        "性别：" + user.getGender() + "\n" +
                        "生日：" + (birthDate != null ? birthDate.format(DATE_FORMATTER) : "未知") + "\n" +
                        "邮箱：" + user.getEmail() + "\n" +
                        "手机号：" + user.getPhone() + "\n" +
                        "今天是：" + today.format(DATE_FORMATTER) + "\n" +  // 修正这里
                        "用户输入：" + userMessage;

        return prompt;
    }
}