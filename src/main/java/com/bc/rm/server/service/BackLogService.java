package com.bc.rm.server.service;

import com.bc.rm.server.entity.BackLog;

import java.util.List;
import java.util.Map;

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

    /**
     * 获取已经结束的待办事项列表
     *
     * @param paramMap 参数map
     * @return 已经结束的待办事项列表
     */
    List<BackLog> getFinishedBackLogListByEpicId(Map<String, String> paramMap);

    /**
     * 获取未结束的待办事项列表
     *
     * @param paramMap 参数map
     * @return 未结束的待办事项列表
     */
    List<BackLog> getUnFinishedBackLogListByEpicId(Map<String, String> paramMap);
}
