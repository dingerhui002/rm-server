package com.bc.rm.server.service;

import com.bc.rm.server.entity.BackLog;

import java.util.List;

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

    List<BackLog> getAllBackLogList();
}
