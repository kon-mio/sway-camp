package com.zxy.swaycamp.aspect;

import com.zxy.swaycamp.annotation.LoginCheck;
import com.zxy.swaycamp.common.constant.CacheConstants;
import com.zxy.swaycamp.common.constant.HttpStatus;
import com.zxy.swaycamp.common.constant.RoleConst;
import com.zxy.swaycamp.common.constant.TimeConst;
import com.zxy.swaycamp.common.enums.CodeMsg;
import com.zxy.swaycamp.common.exception.ServiceException;
import com.zxy.swaycamp.domain.model.LoginUser;
import com.zxy.swaycamp.utils.SwayUtil;
import com.zxy.swaycamp.utils.redis.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 权限校验切面
 *
 * @author XinYuan Zhao
 * @since 2023/1/25
 */

@Aspect
@Component
@Slf4j
public class LoginCheckAspect {

    @Resource
    private RedisCache redisCache;

    @Before("@annotation(loginCheck)")
    public void around(LoginCheck loginCheck) throws Throwable {
        LoginUser user = redisCache.getCacheObject(CacheConstants.LOGIN_USER_KEY + SwayUtil.getCurrentUserId());
        if (user == null) {
            throw new ServiceException(CodeMsg.LOGIN_EXPIRED.getMsg());
        }
        // 用户权限为 1，接口所需权限不为 1则返回
        if (loginCheck.value() != RoleConst.ROLE_USER && user.getUserRole() == RoleConst.ROLE_USER) {
            throw new ServiceException(HttpStatus.FORBIDDEN, "权限不足");
        }
        // 用户权限为 2，接口所需权限为 0 则返回
        if (loginCheck.value() == RoleConst.ROLE_SUPER_ADMIN && user.getUserRole() == RoleConst.ROLE_ADMIN) {
            throw new ServiceException(HttpStatus.FORBIDDEN, "权限不足");
        }
    }
}
