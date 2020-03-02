package com.bc.rm.server.mapper;

import com.bc.rm.server.entity.Module;

import java.util.List;

/**
 * 模块
 *
 * @author zhou
 */
public interface ModuleMapper {

    /**
     * 新增模块
     *
     * @param module 模块
     */
    void addModule(Module module);

    /**
     * 获取模块列表
     *
     * @return 模块列表
     */
    List<Module> getModuleList();
}
