package com.bc.rm.server.service.impl;

import com.bc.rm.server.cons.Constant;
import com.bc.rm.server.entity.BackLog;
import com.bc.rm.server.mapper.BackLogMapper;
import com.bc.rm.server.service.BackLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 待办事项业务实现类
 *
 * @author zhou
 */
@Service("backLogService")
public class BackLogServiceImpl implements BackLogService {
    @Resource
    private BackLogMapper backLogMapper;

    /**
     * 新增待办事项
     *
     * @param backLog 待办事项
     */
    @Override
    public void addBackLog(BackLog backLog) {
        backLogMapper.addBackLog(backLog);
    }

    /**
     * 获取已经结束的待办事项列表
     *
     * @param paramMap 参数map
     * @return 已经结束的待办事项列表
     */
    @Override
    public List<BackLog> getFinishedBackLogListByEpicId(Map<String, String> paramMap) {
        return backLogMapper.getFinishedBackLogListByEpicId(paramMap);
    }

    /**
     * 获取未结束的待办事项列表
     *
     * @param paramMap 参数map
     * @return 未结束的待办事项列表
     */
    @Override
    public List<BackLog> getUnFinishedBackLogListByEpicId(Map<String, String> paramMap) {
        return backLogMapper.getUnFinishedBackLogListByEpicId(paramMap);
    }

}
