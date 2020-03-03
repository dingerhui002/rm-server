package com.bc.rm.server.service;

import com.bc.rm.server.entity.Backlog;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 待办事项业务接口
 *
 * @author zhou
 */
public interface BacklogService {

    /**
     * 新增待办事项
     *
     * @param backlog 待办事项
     */
    void addBacklog(Backlog backlog);

    /**
     * 获取待办事项分页列表
     *
     * @param pageNum  当前分页
     * @param pageSize 每个分页大小
     * @return 用户分页列表
     */
    PageInfo<Backlog> getBacklogListByPageInfo(int pageNum, int pageSize);

    /**
     * 根据ID获取待办事项
     *
     * @param backlogId 待办事项ID
     * @return 待办事项
     */
    Backlog getBacklogById(String backlogId);

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
