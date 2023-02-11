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
     * @param loginDTO  登录参数
     * @return 用户信息
     */
   UserVO login(LoginDTO loginDTO);

    /**
     * 获取验证码
     * @param account 邮箱/手机
     */
   void getCode(String account);

    /**
     * 用户注册
     * @param registerDTO 注册参数
     * @return 用户信息
     */
   UserVO register(RegisterDTO registerDTO);

    /**
     * 更新用户密码
     * @param password 新密码
     */
   void updatePassword(String password);

}
