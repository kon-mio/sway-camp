package com.zxy.swaycamp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.swaycamp.domain.entity.SystemLog;
import com.zxy.swaycamp.mapper.SystemLogMapper;
import com.zxy.swaycamp.service.SystemLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *  系统请求日志 服务实现类
 *
 * @author Xinyuan Zhao
 * @since 2023-01-28
 */
@Slf4j
@Service
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper, SystemLog> implements SystemLogService {



    /**
     * 异步入库
     * @param systemLog
     */
    @Async("LogAsync")
    @Override
    public void insert(SystemLog systemLog){
        log.info("打印当前B线程名称：{}",Thread.currentThread().getName());
        try {
            save(systemLog);
        }catch (Exception e) {
            log.error("未知异常：", e);
        }
    }

}
