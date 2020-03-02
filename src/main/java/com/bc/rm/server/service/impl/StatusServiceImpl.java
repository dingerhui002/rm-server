package com.bc.rm.server.service.impl;

import com.bc.rm.server.entity.Status;
import com.bc.rm.server.mapper.StatusMapper;
import com.bc.rm.server.service.StatusService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 状态
 *
 * @author zhou
 */
@Service("statusService")
public class StatusServiceImpl implements StatusService {
    @Resource
    private StatusMapper statusMapper;

    /**
     * 获取状态列表
     *
     * @return 状态列表
     */
    @Override
    public List<Status> getStatusList() {
        return statusMapper.getStatusList();
    }

}
