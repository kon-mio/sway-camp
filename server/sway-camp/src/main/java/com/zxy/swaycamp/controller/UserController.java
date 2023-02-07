package com.zxy.swaycamp.controller;

import com.zxy.swaycamp.domain.dto.LoginDto;
import com.zxy.swaycamp.domain.vo.UserVo;
import com.zxy.swaycamp.service.UserService;
import com.zxy.swaycamp.utils.request.SwayResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  用户信息表 前端控制器
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-01-23
 */
@Validated
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户名、邮箱、手机号/密码登录
     */
    @PostMapping("/login")
    public SwayResult<UserVo> login(@RequestBody @Validated LoginDto loginDto) {
        return SwayResult.success(userService.login(loginDto));
    }


}

