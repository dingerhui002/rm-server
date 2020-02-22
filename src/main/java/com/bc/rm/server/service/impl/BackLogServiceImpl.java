package com.bc.rm.server.service.impl;

import com.bc.rm.server.entity.BackLog;
import com.bc.rm.server.mapper.BackLogMapper;
import com.bc.rm.server.service.BackLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 待办事项业务实现类
 *
 * @author zhou
 */
@Service("backLogService")
public class BackLogServiceImpl implements BackLogService {
    @Resource
    private BackLogMapper backLogMapper;

    /**
     * 新增待办事项
     *
     * @param backLog 待办事项
     */
    @Override
    public void addBackLog(BackLog backLog) {
        backLogMapper.addBackLog(backLog);
    }
}
