package com.cqupt.java.ai.langchain4j;

import io.jsonwebtoken.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Date;
import java.util.UUID;

@SuppressWarnings("all")
@SpringBootTest
public class JWTTest {
    private long time = 1000 * 60 * 60 * 24 ; // token过期时间 一天
    // 密钥 对于 Hash256 密钥长度必须为 32 个字节，即 256 位
    private String key = "admin"; // 密钥
    @Test
    public void testJWT() {
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                //设置 header
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                //设置 payload
                .claim("username","tom")
                .claim("password","123456")
                //设置过期时间
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .setId(UUID.randomUUID().toString())
                //Signature（签名部分） 通过key 对 header 和 payload 进行签名，生成 signature
                .signWith(SignatureAlgorithm.HS256,key)
                //生成 token 字符串 对header  payload 和 signature 进行拼接
                .compact();
        System.out.println(jwtToken);
    }
    @Test
    public void parse(){
        String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InRvbSIsInBhc3N3b3JkIjoiMTIzNDU2IiwiZXhwIjoxNzUwMjQ5ODU3LCJqdGkiOiIyNjk3NzE4Mi0wNGU3LTRkNGYtYTllMi1mYTk2OWIyNzQyYWIifQ.YuMeE1ATWCYZC-w26Mrshc0ZQkliZzC6lTyR1E8mgSc";
        JwtParser jwtParser = Jwts.parser();
        // 设置密钥
        Jws<Claims> claimsJws = jwtParser.setSigningKey(key).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        System.out.println(claims.get("username"));
        System.out.println(claims.get("password"));
        System.out.println(claims.getId());
        System.out.println(claims.getExpiration());
    }
}
