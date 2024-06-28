package com.siyu.common_utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.siyu.common_static.enums.ExceptionEnum;
import com.siyu.service_base.exceptionHandler.GlobalException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
    public static final Integer EXPIRE = 1000 * 60 * 60 * 24;
    public static final String APP_SECRET = "app_secret";

    public static String getToken(Map<String, Object> map) {
        String token = Jwts.builder()
                           .setIssuedAt(new Date())
                           .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                           .addClaims(map)
                           .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                           .compact();
        return token;
    }

    public static Map<String, Object> parseToken(String token) {
        try {
            if(StringUtils.isEmpty(token)) {
                return new HashMap<String, Object>();
            }
            Claims claims = Jwts.parser()
                                .setSigningKey(APP_SECRET)
                                .parseClaimsJws(token)
                                .getBody();
            return claims;
        } catch(ExpiredJwtException e) {
            throw new GlobalException(ExceptionEnum.EXPIRE_TOKEN);
        }
    }
}
