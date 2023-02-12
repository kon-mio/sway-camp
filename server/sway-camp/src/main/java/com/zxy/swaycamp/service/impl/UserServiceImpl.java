package com.zxy.swaycamp.service.impl;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.swaycamp.common.constant.*;
import com.zxy.swaycamp.common.enums.CodeMsg;
import com.zxy.swaycamp.common.enums.Gender;
import com.zxy.swaycamp.common.exception.ServiceException;
import com.zxy.swaycamp.common.mail.MailTemplate;
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
import org.springframework.util.StringUtils;

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
     * @param loginDto  登录参数
     * @return 用户信息
     */
    @Override
    public UserVO login(LoginDTO loginDto) {
        // 查找用户
        User one = lambdaQuery().and(wrapper -> wrapper
                        .eq(User::getUsername, loginDto.getAccount())
                        .or()
                        .eq(User::getEmail, loginDto.getAccount())
                        .or()
                        .eq(User::getPhoneNumber, loginDto.getAccount()))
                .one();
        if (one == null) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "账号未注册");
        }
        if (one.getPassword() == null){
            throw new ServiceException(HttpStatus.BAD_REQUEST, "请使用验证码登录后设置密码");
        }
        //验证棉麻
        if (!SecurityUtil.matchesPassword(loginDto.getPassword(),one.getPassword())){
            throw new ServiceException(HttpStatus.BAD_REQUEST, "密码错误");
        }
        // 生成token
        String token = TokenUtil.createToken(one.getId());
        // 存入用户登录信息和用户token
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(one, loginUser);
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(System.currentTimeMillis() + TimeConst.MILLIS_DAY_SEVEN);
        loginUser.setToken(token);
        redisCache.setCacheObject(CacheConstants.LOGIN_USER_KEY + loginUser.getId(), loginUser, TimeConst.TOKEN_EXPIRE, TimeUnit.DAYS);
        redisCache.setCacheObject(CacheConstants.LOGIN_TOKEN_KEY + loginUser.getId(), token, TimeConst.TOKEN_EXPIRE, TimeUnit.DAYS);
        // 返回用户信息
        UserVO userVo = new UserVO();
        BeanUtils.copyProperties(one, userVo);
        userVo.setToken(token);
        // 信息脱敏
        userVo.setEmail(DesensitizedUtil.email(userVo.getEmail()));
        return userVo;
    }

    /**
     * 用户注册
     * @param registerDto 注册参数
     * @return 用户信息
     */
    @Override
    public UserVO register(RegisterDTO registerDto){
        if(!StringUtils.hasText(registerDto.getAccount())){
            throw new ServiceException(CodeMsg.PARAMETER_ERROR);
        }
        boolean isEmail = registerDto.getAccount().matches(RegexpConst.REGEXP_EMAIL);
        boolean isPhone = registerDto.getAccount().matches(RegexpConst.REGEXP_PHONE);
        if(!isEmail && !isPhone){
            throw new ServiceException("请使用邮箱/手机号");
        }
        // 查找缓存验证码
        Integer code = isEmail ?
                redisCache.getCacheObject(CacheConstants.EMAIL_CODE + registerDto.getAccount()) :
                redisCache.getCacheObject(CacheConstants.PHONE_CODE + registerDto.getAccount());
        if(code == null){
            throw new ServiceException("请获取验证码");
        }
        if(!code.equals(registerDto.getCode())){
            throw new ServiceException("验证码错误");
        }
        // 删除验证码
        redisCache.deleteObject(isEmail ?
                CacheConstants.EMAIL_CODE + registerDto.getAccount() :
                CacheConstants.PHONE_CODE + registerDto.getAccount());

        // 登录/注册
        User one = lambdaQuery()
                .eq(User::getEmail,registerDto.getAccount())
                .or()
                .eq(User::getPhoneNumber,registerDto.getAccount())
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
                 one.setEmail(registerDto.getAccount());
             }else{
                 one.setPhoneNumber(registerDto.getAccount());
             }
             // 性别默认保密
             one.setGender(Gender.GENDER_OTHER.getGender());
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
        userVo.setPhoneNumber(DesensitizedUtil.mobilePhone(userVo.getPhoneNumber()));
        return userVo;
    }

    /**
     * 更新用户密码
     * @param password 新密码
     * @param code 验证码
     */
    @Override
    public void updatePassword(String password, Integer code){
        try{
            // 查找用户
            Integer userId = SwayUtil.getLoginUserId();
            User one = lambdaQuery().eq(User::getId, userId).one();
            if(one == null){
                throw new ServiceException("用户不存在");
            }
            // 校验验证码
            Integer emailCode = redisCache.getCacheObject(CacheConstants.EMAIL_CODE + one.getEmail());
            Integer phoneCode = redisCache.getCacheObject(CacheConstants.PHONE_CODE + one.getPhoneNumber());
            if(emailCode == null && phoneCode == null){
                throw new ServiceException("请获取验证码");
            }
            if(!code.equals(emailCode) && !code.equals(phoneCode)){
                throw new ServiceException("验证码错误");
            }
            // 删除验证码
            redisCache.deleteObject(phoneCode == null ?
                    CacheConstants.EMAIL_CODE + one.getEmail() :
                    CacheConstants.PHONE_CODE + one.getPhoneNumber());
            // 修改密码
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
        mailUtil.sendMailMessage(mail,"SwayCamp", MailTemplate.emailHtml(String.valueOf(code)));
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
