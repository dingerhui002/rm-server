package com.bc.rm.server.mapper;

import com.bc.rm.server.entity.Status;

import java.util.List;

/**
 * 状态
 *
 * @author zhou
 */
public interface StatusMapper {

    /**
     * 获取状态列表
     *
     * @return 状态列表
     */
    List<Status> getStatusList();
}
