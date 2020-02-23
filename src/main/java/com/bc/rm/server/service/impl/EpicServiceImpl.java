package com.bc.rm.server.service.impl;

import com.bc.rm.server.entity.Epic;
import com.bc.rm.server.mapper.EpicMapper;
import com.bc.rm.server.service.EpicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("epicService")
public class EpicServiceImpl implements EpicService {

    @Resource
    private EpicMapper epicMapper;

    @Override
    public List<Epic> getAllEpicList() {
        return epicMapper.getAllEpicList();
    }
}
