package com.zxy.swaycamp.service.impl;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.swaycamp.common.constant.*;
import com.zxy.swaycamp.common.exception.ServiceException;
import com.zxy.swaycamp.domain.dto.LoginDTO;
import com.zxy.swaycamp.domain.dto.RegisterDTO;
import com.zxy.swaycamp.domain.entity.User;
import com.zxy.swaycamp.domain.model.LoginUser;
import com.zxy.swaycamp.domain.vo.UserVO;
import com.zxy.swaycamp.mapper.UserMapper;
import com.zxy.swaycamp.service.UserService;
import com.zxy.swaycamp.utils.SecurityUtil;
import com.zxy.swaycamp.utils.SwayUtil;
import com.zxy.swaycamp.utils.mail.MailUtil;
import com.zxy.swaycamp.utils.redis.RedisCache;
import com.zxy.swaycamp.utils.request.TokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *  用户信息 服务实现类
 *
 * @author Xinyuan Zhao
 * @since 2023-01-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Resource
    private MailUtil mailUtil;
    @Resource
    private RedisCache redisCache;


    /**
     * 用户名、邮箱、手机号/密码登录
     *
     * @param loginDTO  登录参数
     * @return 用户信息
     */
    @Override
    public UserVO login(LoginDTO loginDTO) {
        User one = lambdaQuery().and(wrapper -> wrapper
                        .eq(User::getUsername, loginDTO.getAccount())
                        .or()
                        .eq(User::getEmail, loginDTO.getAccount())
                        .or()
                        .eq(User::getPhoneNumber, loginDTO.getAccount()))
                .one();
        if (one == null) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "账号密码错误");
        }
        if (one.getPassword() == null){
            throw new ServiceException(HttpStatus.BAD_REQUEST, "请使用验证码登录后设置密码");
        }
        if (!SecurityUtil.matchesPassword(loginDTO.getPassword(),one.getPassword())){
            throw new ServiceException(HttpStatus.BAD_REQUEST, "密码错误");
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
        UserVO userVo = new UserVO();
        BeanUtils.copyProperties(one, userVo);
        userVo.setToken(userToken);
        // 信息脱敏
        userVo.setEmail(DesensitizedUtil.email(userVo.getEmail()));
        return userVo;
    }

    /**
     * 用户注册
     * @param registerDTO 注册参数
     * @return 用户信息
     */
    @Override
    public UserVO register(RegisterDTO registerDTO){
        boolean isEmail = registerDTO.getAccount().matches(RegexpConst.REGEXP_EMAIL);
        boolean isPhone = registerDTO.getAccount().matches(RegexpConst.REGEXP_PHONE);
        if(!isEmail && !isPhone){
            throw new ServiceException("请使用邮箱/手机号");
        }
        // 查找缓存验证码
        Integer code = isEmail ?
                redisCache.getCacheObject(CacheConstants.EMAIL_CODE + registerDTO.getAccount()) :
                redisCache.getCacheObject(CacheConstants.PHONE_CODE + registerDTO.getAccount());
        if(code == null){
            throw new ServiceException("请获取验证码");
        }
        if(!code.equals(registerDTO.getCode())){
            throw new ServiceException("验证码错误");
        }

        // 登录/注册
        User one = lambdaQuery()
                .eq(User::getEmail,registerDTO.getAccount())
                .or()
                .eq(User::getPhoneNumber,registerDTO.getAccount())
                .one();
        // 返回参数
        UserVO userVo = new UserVO();
        // 没注册
        if(one == null){
             one = new User();
             String swayId = getAccount();
             one.setUsername(swayId);
             one.setSwayId(swayId);
             // 设置邮箱或手机号
             if(isEmail){
                 one.setEmail(registerDTO.getAccount());
             }else{
                 one.setPhoneNumber(registerDTO.getAccount());
             }
             // 性别默认保密
             one.setGender(0);
             one.setCreateTime(LocalDateTime.now());
             one.setUserRole(RoleConst.ROLE_USER);
             one.setUserStatus(true);
             one.setIsDeleted(false);
             try{
                 save(one);
             }catch (Exception e){
                 throw new ServiceException();
             }
        }

        // 生成Token
        String userToken = TokenUtil.createToken(one.getId());
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(one, loginUser);
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(System.currentTimeMillis() + TimeConst.MILLIS_DAY_SEVEN);
        loginUser.setToken(userToken);
        redisCache.setCacheObject(CacheConstants.LOGIN_USER_KEY + loginUser.getId(), loginUser, TimeConst.TOKEN_EXPIRE, TimeUnit.DAYS);
        redisCache.setCacheObject(CacheConstants.LOGIN_TOKEN_KEY + loginUser.getId(), userToken, TimeConst.TOKEN_EXPIRE, TimeUnit.DAYS);

        BeanUtils.copyProperties(one,userVo);
        userVo.setToken(userToken);
        // 信息脱敏
        userVo.setEmail(DesensitizedUtil.email(userVo.getEmail()));
        return userVo;
    }

    /**
     * 更新用户密码
     */
    @Override
    public void updatePassword(String password){
        try{
            Integer userId = SwayUtil.getLoginUserId();
            password = SecurityUtil.encodePassword(password);
            lambdaUpdate().eq(User::getId,userId)
                    .set(User::getPassword,password)
                    .update();
        }catch (Exception e){
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 获取验证码
     * @param account 邮箱/手机
     */
    public void getCode(String account){
        // 分析邮箱或手机号
        int code = new Random().nextInt(900000) + 100000;
        // 邮箱
        List<String> mail = new ArrayList<>();
        mail.add(account);
        redisCache.setCacheObject(CacheConstants.EMAIL_CODE+account,code,TimeConst.TOKEN_CODE,TimeUnit.MINUTES);
        mailUtil.sendMailMessage(mail,"SwayCamp", String.valueOf(code));
    }

    /**
     * 创建用户账号
     * @return 用户账号
     */
    public String getAccount(){
        String account = RandomUtil.randomNumbers(8);
        User one = lambdaQuery().eq(User::getSwayId,account).one();
        return one == null ? account : getAccount();
    }
}
