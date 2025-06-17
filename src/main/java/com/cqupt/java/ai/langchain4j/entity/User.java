package com.cqupt.java.ai.langchain4j.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户实体类，对应数据库 user 表
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;              // 用户ID
    private String username;      // 用户名
    private String password;      // 密码
    private String email;         // 邮箱
    private String phone;         // 电话号码
    private String nickname;      // 昵称
    private String gender;        // 性别（枚举类型）
    private LocalDate birthDate;  // 出生日期
    private LocalDateTime createdAt; // 创建时间
    private LocalDateTime updatedAt; // 更新时间
    private String token; // 登录凭证
}

