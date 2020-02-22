package com.bc.rm.server.service.impl;

import com.bc.rm.server.entity.Module;
import com.bc.rm.server.mapper.ModuleMapper;
import com.bc.rm.server.service.ModuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("moduleService")
public class ModuleServiceImpl implements ModuleService {

    @Resource
    private ModuleMapper moduleMapper;

    @Override
    public void addModule(Module module) {
        moduleMapper.addModule(module);
    }
}
