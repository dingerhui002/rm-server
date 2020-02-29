package com.bc.rm.server.mapper;

import com.bc.rm.server.entity.Sprint;

import java.util.List;

/**
 * 迭代
 *
 * @author zhou
 */
public interface SprintMapper {
    /**
     * 获取迭代列表
     *
     * @return 用户列表
     */
    List<Sprint> getSprintList();

    /**
     * 新增迭代
     *
     * @param sprint 迭代
     */
    void addSprint(Sprint sprint);
}
