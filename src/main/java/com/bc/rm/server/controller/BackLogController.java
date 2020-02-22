package com.bc.rm.server.controller;

import com.bc.rm.server.entity.BackLog;
import com.bc.rm.server.service.BackLogService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 产品代办事项控制器
 *
 * @author zhou
 */
@RestController
@RequestMapping("/backLogs")
public class BackLogController {

    private static final Logger logger = LoggerFactory.getLogger(BackLogController.class);

    @Resource
    private BackLogService backLogService;

    /**
     * 新增待办事项
     *
     * @param moduleId 模块ID
     * @param type     类型
     * @param status   状态
     * @param title    标题
     * @param deadLine 预计结束时间
     * @return ResponseEntity
     */
    @ApiOperation(value = "新增代办事项", notes = "新增代办事项")
    @PostMapping(value = "/")
    public ResponseEntity<String> addBackLog(
            @RequestParam String moduleId,
            @RequestParam(required = false, defaultValue = "0") String type,
            @RequestParam String status,
            @RequestParam String title,
            @RequestParam String deadLine) {
        ResponseEntity<String> responseEntity;
        try {
            BackLog backLog = new BackLog(moduleId, type, status, title, deadLine);
            backLogService.addBackLog(backLog);
            responseEntity = new ResponseEntity<>("", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("addBackLog error: " + e.getMessage());
            responseEntity = new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}
