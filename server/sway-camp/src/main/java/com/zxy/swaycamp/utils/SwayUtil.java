package com.zxy.swaycamp.utils;

import com.zxy.swaycamp.common.constant.CommonConst;
import com.zxy.swaycamp.common.enums.CodeMsg;
import com.zxy.swaycamp.common.exception.ServiceException;
import com.zxy.swaycamp.utils.request.TokenUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 通用工具类
 *
 * @author XinYuan Zhao
 * @since 2023/1/26
 */
public class SwayUtil {

    private  SwayUtil(){}

    /**
     * 通过 RequestContextHolder 获取 HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if(servletRequestAttributes == null){
            throw new ServiceException(CodeMsg.FAIL.getMsg());
        }
        return servletRequestAttributes.getRequest();
    }

    /**
     * 获取Token
     */
    public static String getToken() {
        return SwayUtil.getRequest().getHeader(CommonConst.TOKEN_HEADER) == null
                ? null
                : SwayUtil.getRequest().getHeader(CommonConst.TOKEN_HEADER).replace(CommonConst.TOKEN_PREFIX,"");
    }

    /**
     * 获取token中的UserId
     */
    public static Integer getCurrentUserId() {
        return SwayUtil.getToken() == null ? null : TokenUtil.getUserId(SwayUtil.getToken());
    }
}
