package com.zxy.swaycamp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxy.swaycamp.domain.dto.LoginDTO;
import com.zxy.swaycamp.domain.dto.RegisterDTO;
import com.zxy.swaycamp.domain.entity.User;
import com.zxy.swaycamp.domain.vo.UserVO;

/**
 * 用户信息 服务类
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
   UserVO login(LoginDTO loginDto);

    /**
     * 用户注册
     * @param registerDto 注册参数
     * @return 用户信息
     */
   UserVO register(RegisterDTO registerDto);

   /**
     * 根据token获取用户信息
     * @return 用户信息
     */
   UserVO getUserInfo();

    /**
     * 更新用户密码
     * @param password 新密码
     * @param code 验证码
     */
   void updatePassword(String password,Integer code);

    /**
     * 获取验证码
     * @param account 邮箱/手机
     */
    void getCode(String account);

}
