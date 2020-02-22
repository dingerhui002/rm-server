package com.bc.rm.server.mapper;

import com.bc.rm.server.entity.BackLog;

import java.util.List;

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

    List<BackLog> getAllBackLogList();
}
