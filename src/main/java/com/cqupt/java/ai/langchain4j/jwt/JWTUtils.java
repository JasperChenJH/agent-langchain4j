package com.cqupt.java.ai.langchain4j.jwt;

import com.cqupt.java.ai.langchain4j.entity.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
@SuppressWarnings("all")
@Component
public class JWTUtils {
    private static final long time = 1000 * 60 * 60 * 24 ; // token过期时间 一天
    // 密钥 对于 Hash256 密钥长度必须为 32 个字节，即 256 位
    private static final String key = "chen_jin_hui"; // 密钥
    public static String createJWTToken(User user){
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                //设置 header
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                //设置 payload
                .claim("id",user.getId().toString())
                .claim("password", user.getPassword())
                //设置过期时间
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .setId(UUID.randomUUID().toString())
                //Signature（签名部分） 通过key 对 header 和 payload 进行签名，生成 signature
                .signWith(SignatureAlgorithm.HS256,key)
                //生成 token 字符串 对header  payload 和 signature 进行拼接
                .compact();
        return jwtToken;
    }
    public static Claims parse(User user){
        String jwtToken = user.getToken();
        JwtParser jwtParser = Jwts.parser();
        // 设置密钥
        Jws<Claims> claimsJws = jwtParser.setSigningKey(key).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
       return claims;
    }
}
