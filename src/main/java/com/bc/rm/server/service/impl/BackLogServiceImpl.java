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

    @Override
    public List<BackLog> getAllBackLogList() {
        return backLogMapper.getAllBackLogList();
    }

    @Override
    public List<BackLog> getFinishedBackLogListByEpicId(Map<String, String> paramMap) {
//        Map<String, String> paramMap = new HashMap<>();
//        paramMap.put("epicId", epicId);
//        // TODO
//        // 后面status可以做成流程化配置
//        paramMap.put("finishStatus", Constant.FINISH_STATUS);
        return backLogMapper.getFinishedBackLogListByEpicId(paramMap);
    }

    @Override
    public List<BackLog> getUnFinishedBackLogListByEpicId(Map<String, String> paramMap) {
//        Map<String, String> paramMap = new HashMap<>();
//        paramMap.put("epicId", epicId);
//        // TODO
//        // 后面status可以做成流程化配置
//        paramMap.put("finishStatus", Constant.FINISH_STATUS);
        return backLogMapper.getUnFinishedBackLogListByEpicId(paramMap);
    }

}
