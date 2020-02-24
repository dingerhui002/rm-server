package com.bc.rm.server.service;

import com.bc.rm.server.entity.Epic;

import java.util.List;

/**
 * 战略
 *
 * @author zhou
 */
public interface EpicService {

    /**
     * 获取战略列表
     *
     * @return 战略列表
     */
    List<Epic> getAllEpicList();
}
