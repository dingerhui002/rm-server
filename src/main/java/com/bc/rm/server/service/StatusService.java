package com.bc.rm.server.service;

import com.bc.rm.server.entity.Status;

import java.util.List;

/**
 * 状态
 *
 * @author zhou
 */
public interface StatusService {

    /**
     * 获取状态列表
     *
     * @return 状态列表
     */
    List<Status> getStatusList();
}
