package com.zxy.swaycamp.controller;

import com.zxy.swaycamp.annotation.Log;
import com.zxy.swaycamp.annotation.LoginCheck;
import com.zxy.swaycamp.domain.request.LoginBody;
import com.zxy.swaycamp.domain.vo.UserVo;
import com.zxy.swaycamp.service.UserService;
import com.zxy.swaycamp.utils.request.SwayResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

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
    public SwayResult<UserVo> login(@RequestBody @Validated LoginBody loginBody) {
        return userService.login(loginBody.getAccount(), loginBody.getPassword(), loginBody.getIsAdmin());
    }

    @Log
    @LoginCheck(0)
    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @Log
    @PostMapping("/test2")
    public SwayResult test2(@RequestBody @Validated LoginBody loginBody) {
        return SwayResult.success();
    }

}

