package com.maple.demo.service.impl;

import com.maple.demo.entity.OperateLog;
import com.maple.demo.mapper.OperateLogMapper;
import com.maple.demo.service.IOperateLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统管理-操作日志记录 服务实现类
 * </p>
 *
 * @author 笑小枫
 * @since 2022-07-21
 */
@Service
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLog> implements IOperateLogService {

}
