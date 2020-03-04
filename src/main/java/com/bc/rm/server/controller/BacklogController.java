package com.bc.rm.server.controller;

import com.bc.rm.server.cons.Constant;
import com.bc.rm.server.entity.Backlog;
import com.bc.rm.server.entity.User;
import com.bc.rm.server.service.BacklogService;
import com.bc.rm.server.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Resource
    private UserService userService;

    /**
     * 新增待办事项
     *
     * @param title         标题
     * @param statusId      状态ID
     * @param currentUserId 当前处理人ID
     * @param moduleId      模块ID
     * @param sprintId      迭代ID
     * @param priorityOrder 优先级顺序
     * @param priority      优先级
     * @param importance    重要程度
     * @return ResponseEntity<Backlog>
     */
    @ApiOperation(value = "新增代办事项", notes = "新增代办事项")
    @PostMapping(value = "")
    public ResponseEntity<Backlog> addBacklog(
            @RequestParam String type,
            @RequestParam String title,
            @RequestParam String statusId,
            @RequestParam String currentUserId,
            @RequestParam(required = false) String moduleId,
            @RequestParam(required = false) String sprintId,
            @RequestParam(required = false, defaultValue = Constant.IS_LINK_SPRINT_NO) String isLinkSprint,
            @RequestParam(required = false) String beginDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false, defaultValue = "1") String priorityOrder,
            @RequestParam(defaultValue = Constant.PRIORITY_MEDIUM) String priority,
            @RequestParam(defaultValue = Constant.IMPORTANCE_COMMON) String importance) {
        ResponseEntity<Backlog> responseEntity;
        try {
            Backlog backlog = new Backlog(type, title, statusId, currentUserId, moduleId,
                    sprintId, isLinkSprint, beginDate, endDate, priorityOrder, priority, importance);
            User currentUser = userService.getUserByUserId(currentUserId);
            backlog.setCurrentUserName(currentUser.getName());
            logger.info("[addBacklog], data: " + backlog);
            backlogService.addBacklog(backlog);

            backlog = backlogService.getBacklogById(backlog.getId());
            responseEntity = new ResponseEntity<>(backlog, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("addBacklog error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new Backlog(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 获取待办事项列表
     *
     * @param page  当前分页
     * @param limit 每个分页大小
     * @return ResponseEntity
     */
    @ApiOperation(value = "获取代办事项列表", notes = "获取代办事项列表")
    @GetMapping(value = "")
    public ResponseEntity<PageInfo<Backlog>> getBacklogList(
            @RequestParam Integer page,
            @RequestParam Integer limit) {
        logger.info("[getBacklogList] page: " + page + ", limit: " + limit);
        PageInfo<Backlog> pageInfo = backlogService.getBacklogListByPageInfo(page, limit);
        return new ResponseEntity<>(pageInfo, HttpStatus.OK);
    }
}
