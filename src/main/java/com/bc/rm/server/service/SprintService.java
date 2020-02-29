package com.bc.rm.server.service;

import com.bc.rm.server.entity.Sprint;
import com.github.pagehelper.PageInfo;

/**
 * 迭代
 *
 * @author zhou
 */
public interface SprintService {
    /**
     * 获取迭代分页列表
     *
     * @param pageNum  当前分页
     * @param pageSize 每个分页大小
     * @return 迭代分页列表
     */
    PageInfo<Sprint> getSprintListByPageInfo(int pageNum, int pageSize);

    /**
     * 新增迭代
     *
     * @param sprint 迭代
     */
    void addSprint(Sprint sprint);
}
