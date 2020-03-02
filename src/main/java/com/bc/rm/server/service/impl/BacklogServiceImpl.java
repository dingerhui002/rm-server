package com.bc.rm.server.service.impl;

import com.bc.rm.server.entity.Backlog;
import com.bc.rm.server.mapper.BacklogMapper;
import com.bc.rm.server.service.BacklogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 待办事项业务实现类
 *
 * @author zhou
 */
@Service("backlogService")
public class BacklogServiceImpl implements BacklogService {
    @Resource
    private BacklogMapper backlogMapper;

    /**
     * 新增待办事项
     *
     * @param backlog 待办事项
     */
    @Override
    public void addBacklog(Backlog backlog) {
        backlogMapper.addBacklog(backlog);
    }

    /**
     * 获取已经结束的待办事项列表
     *
     * @param paramMap 参数map
     * @return 已经结束的待办事项列表
     */
    @Override
    public List<Backlog> getFinishedBacklogListByEpicId(Map<String, String> paramMap) {
        return backlogMapper.getFinishedBacklogListByEpicId(paramMap);
    }

    /**
     * 获取未结束的待办事项列表
     *
     * @param paramMap 参数map
     * @return 未结束的待办事项列表
     */
    @Override
    public List<Backlog> getUnFinishedBacklogListByEpicId(Map<String, String> paramMap) {
        return backlogMapper.getUnFinishedBacklogListByEpicId(paramMap);
    }

}
