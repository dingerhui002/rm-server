package com.bc.rm.server.service.impl;

import com.bc.rm.server.entity.Backlog;
import com.bc.rm.server.mapper.BacklogMapper;
import com.bc.rm.server.service.BacklogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
     * 获取待办事项分页列表
     *
     * @param pageNum  当前分页
     * @param pageSize 每个分页大小
     * @return 用户分页列表
     */
    @Override
    public PageInfo<Backlog> getBacklogListByPageInfo(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Backlog> backlogList = backlogMapper.getBacklogList();
        PageInfo<Backlog> pageInfo = new PageInfo<>(backlogList);
        return pageInfo;
    }

    /**
     * 根据ID获取待办事项
     *
     * @param backlogId 待办事项ID
     * @return 待办事项
     */
    @Override
    public Backlog getBacklogById(String backlogId) {
        List<Backlog> backlogList = backlogMapper.getBacklogById(backlogId);
        if (CollectionUtils.isEmpty(backlogList)) {
            return new Backlog();
        } else {
            return backlogList.get(0);
        }
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
