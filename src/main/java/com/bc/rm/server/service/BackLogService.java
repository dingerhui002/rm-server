package com.bc.rm.server.service;

import com.bc.rm.server.entity.BackLog;

/**
 * 待办事项业务接口
 *
 * @author zhou
 */
public interface BackLogService {

    /**
     * 新增待办事项
     *
     * @param backLog 待办事项
     */
    void addBackLog(BackLog backLog);
}
