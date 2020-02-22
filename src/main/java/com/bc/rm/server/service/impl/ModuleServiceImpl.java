package com.bc.rm.server.service.impl;

import com.bc.rm.server.entity.Module;
import com.bc.rm.server.mapper.ModuleMapper;
import com.bc.rm.server.service.ModuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 模块业务实现类
 *
 * @author zhou
 */
@Service("moduleService")
public class ModuleServiceImpl implements ModuleService {

    @Resource
    private ModuleMapper moduleMapper;

    /**
     * 新增模块
     *
     * @param module 模块
     */
    @Override
    public void addModule(Module module) {
        moduleMapper.addModule(module);
    }
}
