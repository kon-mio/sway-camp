package com.zxy.swaycamp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.swaycamp.common.constant.CacheConstants;
import com.zxy.swaycamp.common.constant.TimeConst;
import com.zxy.swaycamp.domain.entity.User;
import com.zxy.swaycamp.domain.model.LoginUser;
import com.zxy.swaycamp.domain.vo.UserVo;
import com.zxy.swaycamp.mapper.UserMapper;
import com.zxy.swaycamp.service.UserService;
import com.zxy.swaycamp.utils.redis.RedisCache;
import com.zxy.swaycamp.utils.request.SwayResult;
import com.zxy.swaycamp.utils.request.TokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  用户信息 服务实现类
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-01-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private RedisCache redisCache;

    @Override
    public SwayResult<UserVo> login(String account, String password, Boolean isAdmin) {
        User one = lambdaQuery().and(wrapper -> wrapper
                        .eq(User::getUsername, account)
                        .or()
                        .eq(User::getEmail, account)
                        .or()
                        .eq(User::getPhoneNumber, account))
                .eq(User::getPassword, password)
                .one();

        if (one == null) {
            return SwayResult.fail("账号/密码错误，请重新输入！");
        }

        // 生成Token
        String userToken = TokenUtil.createToken(one.getId());

        // 方案一、分别存入用户登录信息和用户Token，Token设置过期时间
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(one, loginUser);
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(System.currentTimeMillis() + TimeConst.MILLIS_DAY_SEVEN);
        loginUser.setToken(userToken);
        redisCache.setCacheObject(CacheConstants.LOGIN_USER_KEY + loginUser.getId(), loginUser, TimeConst.TOKEN_EXPIRE, TimeUnit.DAYS);
        redisCache.setCacheObject(CacheConstants.LOGIN_TOKEN_KEY + loginUser.getId(), userToken, TimeConst.TOKEN_EXPIRE, TimeUnit.DAYS);

        // 方案二、存入返回用户信息
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(one, userVo);
        userVo.setPassword(null);
        userVo.setAccessToken(userToken);
        return SwayResult.success(userVo);
    }
}
