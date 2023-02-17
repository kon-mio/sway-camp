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
import com.zxy.swaycamp.domain.vo.TokenVO;
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
import java.sql.Time;
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

        LoginUser loginUser = new LoginUser();
        loginUser.setId(one.getId());
        loginUser.setUserRole(one.getUserRole());
        TokenVO tokenVO = createToken(loginUser);

        // 返回用户信息
        UserVO userVo = new UserVO();
        BeanUtils.copyProperties(one, userVo);
        userVo.setAccessToken(tokenVO.getAccessToken());
        userVo.setRefreshToken(tokenVO.getRefreshToken());
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
        LoginUser loginUser = new LoginUser();
        loginUser.setId(one.getId());
        loginUser.setUserRole(one.getUserRole());
        TokenVO tokenVO = createToken(loginUser);

        // 返回用户信息
        UserVO userVo = new UserVO();
        BeanUtils.copyProperties(one, userVo);
        userVo.setAccessToken(tokenVO.getAccessToken());
        userVo.setRefreshToken(tokenVO.getRefreshToken());
        // 信息脱敏
        userVo.setEmail(DesensitizedUtil.email(userVo.getEmail()));
        return userVo;
    }


    /**
     * 根据token获取用户信息
     * @return 用户信息
     */
    @Override
    public UserVO getUserInfo(){
        Integer userId = SwayUtil.getCurrentUserId();
        User user = getById(userId);
        if(user == null){
            throw new ServiceException("用户不存在");
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return userVO;
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
            Integer userId = SwayUtil.getCurrentUserId();
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


    /**
     * 刷新token
     * @return 双token
     */
    @Override
    public TokenVO refreshToken(){
        Integer userId = SwayUtil.getCurrentUserId();
        String refreshToken = redisCache.getCacheObject(CacheConstants.LOGIN_TOKEN_REFRESH_KEY + userId);
        if (refreshToken == null || !SwayUtil.getToken().equals(refreshToken)) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "请求token过期，请重新登录");
        }
        LoginUser loginUser = redisCache.getCacheObject(CacheConstants.LOGIN_USER_KEY + userId);
        if(loginUser == null){
            throw new ServiceException("请求错误");
        }
        return createToken(loginUser);
    }

    /**
     * 生成Token并存入Redis
     *
     * @param loginUser 登录用户
     * @return token
     */
    private TokenVO createToken(LoginUser loginUser){
        TokenVO tokenVO = new TokenVO();
        tokenVO.setAccessToken(TokenUtil.createToken(loginUser.getId(), TimeConst.TOKEN_EXPIRE_ACCESS));
        tokenVO.setRefreshToken(TokenUtil.createToken(loginUser.getId(), TimeConst.TOKEN_EXPIRE_REFRESH));
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(System.currentTimeMillis() + TimeConst.MILLIS_DAY_SEVEN);
        loginUser.setAccessToken(tokenVO.getAccessToken());
        redisCache.setCacheObject(CacheConstants.LOGIN_USER_KEY + loginUser.getId(), loginUser, TimeConst.TOKEN_EXPIRE_REFRESH, TimeUnit.DAYS);
        redisCache.setCacheObject(CacheConstants.LOGIN_TOKEN_ACCESS_KEY + loginUser.getId(), tokenVO.getAccessToken(), TimeConst.TOKEN_EXPIRE_ACCESS, TimeUnit.DAYS);
        redisCache.setCacheObject(CacheConstants.LOGIN_TOKEN_REFRESH_KEY + loginUser.getId(), tokenVO.getRefreshToken(), TimeConst.TOKEN_EXPIRE_REFRESH, TimeUnit.DAYS);
        return tokenVO;
    }
}
