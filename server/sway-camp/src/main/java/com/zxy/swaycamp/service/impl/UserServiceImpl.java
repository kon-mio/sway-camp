package com.zxy.swaycamp.service.impl;

import cn.hutool.core.util.DesensitizedUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.swaycamp.common.constant.CacheConstants;
import com.zxy.swaycamp.common.constant.HttpStatus;
import com.zxy.swaycamp.common.constant.TimeConst;
import com.zxy.swaycamp.common.enums.CodeMsg;
import com.zxy.swaycamp.common.exception.ServiceException;
import com.zxy.swaycamp.domain.dto.LoginDto;
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

    /**
     * 用户名、邮箱、手机号/密码登录
     *
     * @param loginDto  登录参数
     * @return 用户信息
     */
    @Override
    public UserVo login(LoginDto loginDto) {
        User one = lambdaQuery().and(wrapper -> wrapper
                        .eq(User::getUsername, loginDto.getAccount())
                        .or()
                        .eq(User::getEmail, loginDto.getAccount())
                        .or()
                        .eq(User::getPhoneNumber, loginDto.getAccount()))
                .eq(User::getPassword, loginDto.getPassword())
                .one();

        if (one == null) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "账号密码错误");
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
        userVo.setToken(userToken);
        // 信息脱敏
        userVo.setEmail(DesensitizedUtil.email(userVo.getEmail()));
        return userVo;
    }
}
