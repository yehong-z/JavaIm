package com.zyh.javaim.logic.common.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyh.javaim.logic.common.context.UserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtGenerator {
    public static String SecretKey = "secretKey_secretKey_secretKey_secretKey_secretKey";

    public static String GenerateJwtToken(UserDetail userDetail) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        String subject = "zyh";
        // 生成签名密钥
        byte[] apiKeySecretBytes = JwtGenerator.SecretKey.getBytes();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String udStr = objectMapper.writeValueAsString(userDetail);
            return Jwts.builder()
                    .setSubject(subject)
                    .setIssuedAt(now)
                    .signWith(SignatureAlgorithm.HS256, apiKeySecretBytes)
                    .claim("detail", udStr)
                    .compact();
        } catch (Exception e) {
            log.error(e.toString());
        }
        return "";
    }

    public static UserDetail parseJwtToken(String jwt) {

        // 使用 HMAC SHA-256 签名算法解析 JWT
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(JwtGenerator.SecretKey.getBytes())
                .build()
                .parseClaimsJws(jwt);

        // 获取 JWT 中的信息
        // 其他信息的获取方式，例如：body.get("keyName")
        String ud = claimsJws.getBody().get("detail", String.class);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UserDetail userDetail = objectMapper.readValue(ud, UserDetail.class);
            return userDetail;
        } catch (Exception e) {

            log.error("parseJwtToken" + e);
        }

        return new UserDetail();
        // 可以在这里根据需要进行业务逻辑处理

    }
    public static void main(String[] args) {

        String subject = "user123";
        Map<String, Object> userClaims = new HashMap<>();

        String jwt = GenerateJwtToken(new UserDetail());
        System.out.println("Generated JWT: " + jwt);
        UserDetail res = parseJwtToken(jwt);
        System.out.println(res);
    }
}