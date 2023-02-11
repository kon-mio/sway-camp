package com.zxy.swaycamp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.swaycamp.domain.entity.SystemLog;
import com.zxy.swaycamp.mapper.SystemLogMapper;
import com.zxy.swaycamp.service.SystemLogService;
import org.springframework.stereotype.Service;

/**
 *  系统请求日志 服务实现类
 *
 * @author Xinyuan Zhao
 * @since 2023-01-28
 */
@Service
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper, SystemLog> implements SystemLogService {

}
