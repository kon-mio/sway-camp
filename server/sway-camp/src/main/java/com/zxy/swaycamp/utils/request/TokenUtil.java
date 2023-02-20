package com.zxy.swaycamp.utils.request;

import com.zxy.swaycamp.common.constant.CommonConst;
import com.zxy.swaycamp.common.constant.HttpStatus;
import com.zxy.swaycamp.common.exception.ServiceException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


/**
 * Token工具
 *
 * @author XinYuan Zhao
 * @since 2023/1/25
 */
@Slf4j
public class TokenUtil {

    public static final String JWT_SECRET_KEY = "SZ65LG18FZ7LPVUK127OK5DCY0N4PNFN";

    /**
     * 创建Token
     *
     * @param userId 用户ID
     * @param expireTime 过期时间天
     * @return Token
     */
    public static String createToken(Integer userId, Integer expireTime) {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        // 设置签发时间
        calendar.setTime(new Date());
        // 设置过期时间
        calendar.add(Calendar.DATE, expireTime);
        Date time = calendar.getTime();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", userId);
        return Jwts.builder()
                .setClaims(map)
                //签发时间
                .setIssuedAt(now)
                //过期时间
                .setExpiration(time)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    /**
     * 解析token
     * @param token token
     */
    private static Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(JWT_SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new ServiceException(HttpStatus.FORBIDDEN, "token已过期");
        } catch (Exception e){
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "token不合法");
        }
    }

     /**
     * token是否过期
     * @param claims token
     */
    public static boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    /**
     * 获取用户ID
     * @param token token
     * @return 用户ID
     */
    public static Integer getUserId(String token) {
        if (token == null) {
            return null;
        }
        token = token.replace(CommonConst.TOKEN_PREFIX, "");
        Claims claims = getClaimByToken(token);
        if(claims == null) {
            return null;
        }
        if(isTokenExpired(claims)){
            throw new ServiceException(HttpStatus.FORBIDDEN, "token已过期");
        }
        if(claims.get("id") == null){
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "token不合法");
        }
        return (Integer) claims.get("id");
    }
}
