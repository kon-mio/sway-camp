package com.zxy.swaycamp.aspect;


import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.zxy.swaycamp.annotation.Log;
import com.zxy.swaycamp.common.constant.CommonConst;
import com.zxy.swaycamp.common.filter.PropertyPreExcludeFilter;
import com.zxy.swaycamp.domain.entity.SystemLog;
import com.zxy.swaycamp.service.SystemLogService;
import com.zxy.swaycamp.utils.ServletUtil;
import com.zxy.swaycamp.utils.SwayUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作日志记录处理
 *
 * @author XinYuan Zhao
 * @since 2023/1/28
 */
@Aspect
@Component
public class LogAspect {

    /**
     * 排除敏感属性字段
     */
    protected static final String[] EXCLUDE_PROPERTIES = {"password", "oldPassword", "newPassword", "confirmPassword"};
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    @Resource
    private SystemLogService systemLogService;

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult) {
        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    /**
     * 拦截异常操作 无法捕获 validation校验错误 需要添加 BindingResult
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e) {
        handleLog(joinPoint, controllerLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, Log log, final Exception e, Object jsonResult) {
        try {
            // *========数据库日志=========*//
            SystemLog systemLog = new SystemLog();
            systemLog.setStatus(CommonConst.STATUS_TRUE);
            // 获取当前的用户ID
            Integer userId = SwayUtil.getLoginUserId();
            if (userId != null) {
                systemLog.setRequestUser(userId);
            }
            // 错误信息
            if (e != null) {
                systemLog.setStatus(CommonConst.STATUS_FALSE);
                systemLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            systemLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            systemLog.setType(SwayUtil.getRequest().getMethod());
            // 参数
            systemLog.setParam(getParameterToJson(joinPoint));
            systemLog.setBrowser(ServletUtil.getBrowser());
            systemLog.setRequestSystem(ServletUtil.getSystem());
            systemLog.setRequestIp(ServletUtil.getIp());
            systemLog.setAddress(ServletUtil.getHttpCityInfo(systemLog.getRequestIp()));
            // 处理设置注解上的参数
            systemLog.setAction(log.action().toString());
            // 设置标题
            systemLog.setTitle(log.title());
            systemLog.setResult(JSON.toJSONString(jsonResult));
            systemLog.setRequestTime(LocalDateTime.now());
            // 保存数据库
            systemLogService.save(systemLog);
        } catch (Exception exp) {
            logger.error("异常信息:{}", exp.getMessage());
        }
    }


    /**
     * 忽略敏感属性
     */
    public PropertyPreExcludeFilter excludePropertyPreFilter() {
        return new PropertyPreExcludeFilter().addExcludes(EXCLUDE_PROPERTIES);
    }

    /**
     * 获取参数（转换json格式）
     */
    public String getParameterToJson(JoinPoint point) {
        List<Object> argList = new ArrayList<>();
        //参数值
        Object[] argValues = point.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature) point.getSignature()).getParameterNames();
        if (argValues != null) {
            for (int i = 0; i < argValues.length; i++) {
                Map<String, Object> map = new HashMap<>();
                String key = argNames[i];
                map.put(key, argValues[i]);
                argList.add(map);
            }
        }
        if (argList.isEmpty()) {
            return "";
        }
        return argList.size() == 1 ? JSONUtil.toJsonStr(argList.get(0)) : JSONUtil.toJsonStr(argList);
    }

}
