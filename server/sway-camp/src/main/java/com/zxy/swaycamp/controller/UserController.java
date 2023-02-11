package com.zxy.swaycamp.controller;

import com.zxy.swaycamp.annotation.Log;
import com.zxy.swaycamp.annotation.LoginCheck;
import com.zxy.swaycamp.common.constant.CommonConst;
import com.zxy.swaycamp.common.enums.Action;
import com.zxy.swaycamp.common.enums.CodeMsg;
import com.zxy.swaycamp.domain.dto.LoginDto;
import com.zxy.swaycamp.domain.dto.RegisterDto;
import com.zxy.swaycamp.domain.vo.UserVo;
import com.zxy.swaycamp.service.UserService;
import com.zxy.swaycamp.utils.mail.MailUtil;
import com.zxy.swaycamp.utils.request.SwayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  用户信息表 前端控制器
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
     * 密码登录
     *
     * @param loginDto 登录信息
     * @return 用户信息
     */
    @Log(title="用户登录",action = Action.SELECT)
    @PostMapping("/login")
    public SwayResult<UserVo> login(@RequestBody @Validated LoginDto loginDto) {
        return SwayResult.success(userService.login(loginDto));
    }

    /**
     * 用户注册
     *
     * @param registerDto 注册参数
     * @return 用户信息
     */
    @PostMapping("/register")
    public SwayResult<UserVo> register(@RequestBody @Validated RegisterDto registerDto) {
        return SwayResult.success(userService.register(registerDto));
    }

    /**
     * 获取验证码
     *
     * @param account 邮箱/手机号
     */
    @PostMapping("/code")
    public SwayResult<UserVo> getCode(@RequestBody Map<String,String> account) {
        if( account == null || account.get(CommonConst.LITERAL_ACCOUNT) == null){
            SwayResult.fail(CodeMsg.PARAMETER_ERROR);
        }else{
            userService.getCode(account.get(CommonConst.LITERAL_ACCOUNT));
        }
        return SwayResult.success();
    }

    /**
     * 获取验证码
     *
     * @param password 密码
     */
    @LoginCheck
    @PostMapping("/password")
    public SwayResult<UserVo> updatePassword(@RequestBody Map<String,String> password) {
        if( password == null || password.get(CommonConst.LITERAL_PASSWORD) == null){
            SwayResult.fail(CodeMsg.PARAMETER_ERROR);
        }else{
            userService.updatePassword(password.get(CommonConst.LITERAL_PASSWORD));
        }
        return SwayResult.success();
    }
}

