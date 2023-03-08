package com.zxy.swaycamp.controller;

import cn.hutool.core.util.DesensitizedUtil;
import com.zxy.swaycamp.annotation.Log;
import com.zxy.swaycamp.annotation.LoginCheck;
import com.zxy.swaycamp.common.constant.CacheConstants;
import com.zxy.swaycamp.common.constant.CommonConst;
import com.zxy.swaycamp.common.constant.TimeConst;
import com.zxy.swaycamp.common.enums.Action;
import com.zxy.swaycamp.common.enums.CodeMsg;
import com.zxy.swaycamp.common.exception.ServiceException;
import com.zxy.swaycamp.domain.dto.user.LoginDTO;
import com.zxy.swaycamp.domain.dto.user.RegisterDTO;
import com.zxy.swaycamp.domain.dto.user.UpdateUserInfoDTO;
import com.zxy.swaycamp.domain.entity.User;
import com.zxy.swaycamp.domain.vo.TokenVO;
import com.zxy.swaycamp.domain.vo.UserVO;
import com.zxy.swaycamp.service.UserService;
import com.zxy.swaycamp.utils.SwayUtil;
import com.zxy.swaycamp.utils.file.SwayFileUtil;
import com.zxy.swaycamp.utils.query.CommonQuery;
import com.zxy.swaycamp.utils.request.SwayResult;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    private final CommonQuery commonQuery;

    public UserController(UserService userService,CommonQuery commonQuery) {
        this.userService = userService;
        this.commonQuery = commonQuery;
    }

    // TODO 优化登录校验注解 该注解同日志注解会降低接口速度
    // TODO 优化日志切面 日志注解会降低接口速度约100ms

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
    @Log(title="用户注册",action = Action.INSERT)
    @PostMapping("/register")
    public SwayResult<UserVO> register(@RequestBody @Validated RegisterDTO registerDto) {
        return SwayResult.success(userService.register(registerDto));
    }

    /**
     * 根据token获取用户信息
     * @return 用户信息
     */
    @LoginCheck
    @Log(title="获取用户信息",action = Action.SELECT)
    @GetMapping("/info")
    public SwayResult<UserVO> getUserInfo(){
        // TODO 优化日志切面 日志注解会降低接口速度约100ms
        Integer userId = SwayUtil.getCurrentUserId();
        User user = commonQuery.getUser(userId);
        if(user == null){
            SwayResult.fail("查询失败");
        }
        UserVO userVo = new UserVO();
        BeanUtils.copyProperties(user,userVo);
        userVo.setEmail(DesensitizedUtil.email(userVo.getEmail()));
        userVo.setPhoneNumber(DesensitizedUtil.mobilePhone(userVo.getPhoneNumber()));
        return SwayResult.success(userVo);
    }

    /**
     * 更新用户信息
     *
     * @param updateUserInfoDTO 新的用户信息
     * @return 新的用户信息
     */
    @LoginCheck
    @Log(title="更新用户信息",action = Action.UPDATE)
    @PostMapping("/info/update")
    public SwayResult<UserVO> updateUserInfo(@RequestBody @Validated UpdateUserInfoDTO updateUserInfoDTO){
        return SwayResult.success(userService.updateUserInfo(updateUserInfoDTO));
    }

    /**
     * 更新用户头像
     *
     * @param file 头像文件
     */
    @LoginCheck
    @Log(title="更新用户头像",action = Action.UPDATE)
    @PostMapping("/avatar/update")
    public SwayResult updateAvatar(MultipartFile file){
        if(file == null){
            return SwayResult.fail();
        }
        userService.updateAvatar(file);
        return SwayResult.success();
    }

    /**
     * 获取验证码
     *
     * @param account 邮箱/手机号
     */
    @Log(title="获取验证码",action = Action.SELECT)
    @PostMapping("/code")
    public SwayResult<UserVO> getCode(@RequestBody Map<String,String> account) {
        //TODO 根据IP存入Redis进行限制 同一IP一天最多获取10次, 10分钟内最多获取3次
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
    @Log(title="更新密码",action = Action.UPDATE)
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
    @Log(title="刷新Token",action = Action.UPDATE)
    @GetMapping("/refresh")
    public SwayResult<TokenVO> refreshToken(){
        return SwayResult.success(userService.refreshToken());
    }
}

