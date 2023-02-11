package com.zxy.swaycamp.utils.request;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.zxy.swaycamp.common.constant.CommonConst;
import com.zxy.swaycamp.common.constant.HttpStatus;
import com.zxy.swaycamp.common.exception.ServiceException;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.StringUtils;


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
     * @return Token
     */
    public static String createToken(Integer userId) {
        return JWT.create()
                .withClaim("id", userId)
                // 设置签名 密钥
                .sign(Algorithm.HMAC256(JWT_SECRET_KEY));
    }

    /**
     * 检查Token是否有效
     *
     * @param token Token
     * @return 是否有效
     */
    public static DecodedJWT isValid(String token) {
        if (Strings.isNotBlank(token)) {
            try {
                token = token.replace(CommonConst.TOKEN_PREFIX,"");
                //创建验证对象,这里使用的加密算法和密钥必须与生成TOKEN时的相同否则无法验证
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(JWT_SECRET_KEY)).build();
                //验证JWT
                return jwtVerifier.verify(token);
            } catch (Exception e) {
                throw new ServiceException(HttpStatus.UNAUTHORIZED, "Token校验失败");
            }
        } else {
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "Token不能为空");
        }
    }

    /**
     * 获取Token Claims
     *
     * @param token Token
     * @return Claims
     */
    public static Integer getClaims(String token) {
        System.out.println(token);
        if(!StringUtils.hasText(token)){
            return null;
        }
        return isValid(token).getClaim("id").asInt();
    }

}
