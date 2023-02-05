package com.zxy.swaycamp.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
     * @param account  账号
     * @param password 密码
     * @param isAdmin  是否是管理员登录
     * @return
     */
    SwayResult<UserVo> login(String account, String password, Boolean isAdmin);
}
