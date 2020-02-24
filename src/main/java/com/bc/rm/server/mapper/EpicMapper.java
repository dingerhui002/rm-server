package com.bc.rm.server.mapper;

import com.bc.rm.server.entity.Epic;

import java.util.List;

/**
 * 战略
 *
 * @author zhou
 */
public interface EpicMapper {

    /**
     * 获取战略列表
     *
     * @return 战略列表
     */
    List<Epic> getAllEpicList();
}
