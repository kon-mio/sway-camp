package com.zxy.swaycamp.common.interceptor;

import com.zxy.swaycamp.annotation.LoginCheck;
import com.zxy.swaycamp.common.constant.CacheConstants;
import com.zxy.swaycamp.common.constant.CommonConst;
import com.zxy.swaycamp.common.constant.HttpStatus;
import com.zxy.swaycamp.common.exception.ServiceException;
import com.zxy.swaycamp.utils.SwayUtil;
import com.zxy.swaycamp.utils.redis.RedisCache;
import com.zxy.swaycamp.utils.request.TokenUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author XinYuan Zhao
 * @date 2023/1/25
 * @apiNote
 */
public class TokenInterceptor implements HandlerInterceptor {

    @Resource
    private RedisCache redisCache;

    /**
     * Token拦截器
     *
     * @param request  前端请求信息
     * @param response 返回响应体
     * @param handler  请求接口方法信息（方法名、参数、注解等）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 不是映射到方法
        if(!(handler instanceof HandlerMethod)){
            return false;
        }
        HandlerMethod handle = (HandlerMethod) handler;
        // 获取登录校验注解
        LoginCheck loginCheck = handle.getMethodAnnotation(LoginCheck.class);
        // 不需要登录直接通过
        if (loginCheck == null) {
            return true;
        }

        //验证Token 无Token返回
        if (request.getHeader(CommonConst.TOKEN_HEADER) == null) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "Token为空");
        }
        String token = SwayUtil.getToken();
        if (token == null) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "Token不合法");
        }
        Integer userId = TokenUtil.getClaims(token);
        if (userId == null) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "Token不合法");
        }
        // 验证token是否过期
        String userToken = redisCache.getCacheObject(CacheConstants.LOGIN_TOKEN_KEY + userId);
        if (userToken == null || !SwayUtil.getToken().equals(userToken)) {
            throw new ServiceException(HttpStatus.FORBIDDEN, "Token过期，请重新登录");
        }
        return true;
    }
}