package com.bc.rm.server.mapper;

import com.bc.rm.server.entity.Backlog;

import java.util.List;
import java.util.Map;

/**
 * 待办事项
 *
 * @author zhou
 */
public interface BacklogMapper {

    /**
     * 新增待办事项
     *
     * @param backlog 待办事项
     */
    void addBacklog(Backlog backlog);

    /**
     * 获取待办事项列表
     *
     * @return 待办事项列表
     */
    List<Backlog> getBacklogList();

    /**
     * 根据ID获取待办事项
     *
     * @param backlogId 待办事项ID
     * @return 待办事项
     */
    List<Backlog> getBacklogById(String backlogId);

    /**
     * 获取已经结束的待办事项列表
     *
     * @param paramMap 参数map
     * @return 已经结束的待办事项列表
     */
    List<Backlog> getFinishedBacklogListByEpicId(Map<String, String> paramMap);

    /**
     * 获取未结束的待办事项列表
     *
     * @param paramMap 参数map
     * @return 未结束的待办事项列表
     */
    List<Backlog> getUnFinishedBacklogListByEpicId(Map<String, String> paramMap);
}
