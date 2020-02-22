package com.bc.rm.server.service;

import com.bc.rm.server.entity.Module;

/**
 * 模块业务接口
 *
 * @author zhou
 */
public interface ModuleService {

    /**
     * 新增模块
     *
     * @param module 模块
     */
    void addModule(Module module);
}
