package com.bc.rm.server.controller;

import com.bc.rm.server.entity.Backlog;
import com.bc.rm.server.service.BacklogService;
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
@RequestMapping("/backlogs")
public class BacklogController {

    private static final Logger logger = LoggerFactory.getLogger(BacklogController.class);

    @Resource
    private BacklogService backlogService;

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
    @PostMapping(value = "")
    public ResponseEntity<String> addBacklog(
            @RequestParam String title,
            @RequestParam String statusId,
            @RequestParam String currentUserId,
            @RequestParam String moduleId,
            @RequestParam String sprintId,
            @RequestParam String priorityOrder,
            @RequestParam String priority,
            @RequestParam String importance,
            @RequestParam(required = false) String deadLine) {
        ResponseEntity<String> responseEntity;
        try {
            Backlog backlog = new Backlog(title, statusId, currentUserId, moduleId, sprintId, priorityOrder, priority, importance, deadLine);
            logger.info("[addBacklog], data: " + backlog);
            backlogService.addBacklog(backlog);
            responseEntity = new ResponseEntity<>("", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("addBacklog error: " + e.getMessage());
            responseEntity = new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}
