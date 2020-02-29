package com.bc.rm.server.service.impl;

import com.bc.rm.server.entity.Sprint;
import com.bc.rm.server.mapper.SprintMapper;
import com.bc.rm.server.service.SprintService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 迭代
 *
 * @author zhou
 */
@Service("sprintService")
public class SprintServiceImpl implements SprintService {

    @Resource
    private SprintMapper sprintMapper;

    /**
     * 获取迭代分页列表
     *
     * @param pageNum  当前分页
     * @param pageSize 每个分页大小
     * @return 迭代分页列表
     */
    @Override
    public PageInfo<Sprint> getSprintListByPageInfo(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Sprint> sprintList = sprintMapper.getSprintList();
        PageInfo<Sprint> pageInfo = new PageInfo<>(sprintList);
        return pageInfo;
    }

    /**
     * 新增迭代
     *
     * @param sprint 迭代
     */
    @Override
    public void addSprint(Sprint sprint) {
        sprintMapper.addSprint(sprint);
    }

    /**
     * 修改迭代
     *
     * @param sprint
     */
    @Override
    public void updateSprint(Sprint sprint) {
        sprintMapper.updateSprint(sprint);
    }
}
