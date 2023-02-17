package com.zxy.swaycamp.controller;

import com.zxy.swaycamp.annotation.Log;
import com.zxy.swaycamp.annotation.LoginCheck;
import com.zxy.swaycamp.common.constant.CommonConst;
import com.zxy.swaycamp.common.enums.Action;
import com.zxy.swaycamp.common.enums.CodeMsg;
import com.zxy.swaycamp.domain.dto.LoginDTO;
import com.zxy.swaycamp.domain.dto.RegisterDTO;
import com.zxy.swaycamp.domain.vo.TokenVO;
import com.zxy.swaycamp.domain.vo.UserVO;
import com.zxy.swaycamp.service.UserService;
import com.zxy.swaycamp.utils.request.SwayResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public SwayResult<UserVO> login(@RequestBody @Validated LoginDTO loginDto) {
        return SwayResult.success(userService.login(loginDto));
    }

    /**
     * 用户注册
     *
     * @param registerDto 注册参数
     * @return 用户信息
     */
    @PostMapping("/register")
    public SwayResult<UserVO> register(@RequestBody @Validated RegisterDTO registerDto) {
        return SwayResult.success(userService.register(registerDto));
    }

    /**
     * 根据token获取用户信息
     * @return 用户信息
     */
    @LoginCheck
    @GetMapping("/info")
    public SwayResult<UserVO> getUserInfo(){
        return SwayResult.success(userService.getUserInfo());
    }

    /**
     * 获取验证码
     *
     * @param account 邮箱/手机号
     */
    @PostMapping("/code")
    public SwayResult<UserVO> getCode(@RequestBody Map<String,String> account) {
        if( account == null || account.get(CommonConst.LITERAL_ACCOUNT) == null){
            SwayResult.fail(CodeMsg.PARAMETER_ERROR);
        }else{
            userService.getCode(account.get(CommonConst.LITERAL_ACCOUNT));
        }
        return SwayResult.success();
    }

    /**
     * 修改密码
     *
     * @param updatePassword 密码和验证码
     */
    @LoginCheck
    @PostMapping("/password")
    public SwayResult<UserVO> updatePassword(@RequestBody Map<String,String> updatePassword) {
        if( updatePassword == null
                || updatePassword.get(CommonConst.LITERAL_PASSWORD) == null
                || updatePassword.get(CommonConst.LITERAL_CODE) == null){
            return SwayResult.fail(CodeMsg.PARAMETER_ERROR);
        }
        userService.updatePassword(updatePassword.get(CommonConst.LITERAL_PASSWORD),
                Integer.valueOf(updatePassword.get(CommonConst.LITERAL_CODE)));
        return SwayResult.success();
    }


    /**
     * 刷新token
     * @return 双token
     */
    @GetMapping("/refresh")
    public SwayResult<TokenVO> refreshToken(){
        return SwayResult.success(userService.refreshToken());
    }
}

