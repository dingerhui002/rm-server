package com.bc.rm.server.mapper;

import com.bc.rm.server.entity.BackLog;

/**
 * 待办事项
 *
 * @author zhou
 */
public interface BackLogMapper {

    /**
     * 新增待办事项
     *
     * @param backLog 待办事项
     */
    void addBackLog(BackLog backLog);
}
