package com.edu.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtil {

    /**
     * 生成 JWT 令牌
     * @param secretKey 密钥（JDK 21 下建议至少 32 位长度）
     * @param ttlMillis 过期时间（毫秒）
     * @param claims 自定义载荷
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 生成符合 HS256 算法的密钥（JDK 21 兼容方式）
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);

        // 计算过期时间
        long expirationTime = System.currentTimeMillis() + ttlMillis;
        Date expirationDate = new Date(expirationTime);

        return Jwts.builder()
                .setClaims(claims) // 设置自定义载荷
                .setExpiration(expirationDate) // 设置过期时间
                .signWith(Keys.hmacShaKeyFor(keyBytes), SignatureAlgorithm.HS256) // 签名算法
                .compact();
    }

    /**
     * 解析 JWT 令牌
     */
    public static Claims parseJWT(String secretKey, String token) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);

        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(keyBytes)) // 验证签名的密钥
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}