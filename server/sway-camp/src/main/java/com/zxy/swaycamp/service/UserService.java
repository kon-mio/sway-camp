package com.zxy.swaycamp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxy.swaycamp.domain.dto.LoginDto;
import com.zxy.swaycamp.domain.dto.RegisterDto;
import com.zxy.swaycamp.domain.entity.User;
import com.zxy.swaycamp.domain.vo.UserVo;
import com.zxy.swaycamp.utils.request.SwayResult;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-01-23
 */
public interface UserService extends IService<User> {

    /**
     * 用户名、邮箱、手机号/密码登录
     *
     * @param loginDto  登录参数
     * @return 用户信息
     */
   UserVo login(LoginDto loginDto);

    /**
     * 获取验证码
     * @param account 邮箱/手机
     */
   void getCode(String account);

    /**
     * 用户注册
     * @param registerDto 注册参数
     * @return 用户信息
     */
   UserVo register(RegisterDto registerDto);

    /**
     * 更新用户密码
     */
   void updatePassword(String password);

}
