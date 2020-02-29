package com.bc.rm.server.controller;

import com.bc.rm.server.entity.Sprint;
import com.bc.rm.server.entity.User;
import com.bc.rm.server.enums.ResponseMsg;
import com.bc.rm.server.service.SprintService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 迭代控制器
 *
 * @author zhou
 */
@RestController
@RequestMapping("/sprints")
public class SprintController {

    private static final Logger logger = LoggerFactory.getLogger(SprintController.class);

    @Resource
    private SprintService sprintService;

    @ApiOperation(value = "获取迭代列表", notes = "获取迭代列表")
    @GetMapping(value = "")
    public ResponseEntity<PageInfo<Sprint>> getSprintList(
            @RequestParam Integer page,
            @RequestParam Integer limit) {
        logger.info("page: " + page + ", limit: " + limit);
        PageInfo<Sprint> pageInfo = sprintService.getSprintListByPageInfo(page, limit);
        return new ResponseEntity<>(pageInfo, HttpStatus.OK);
    }

    @ApiOperation(value = "创建迭代", notes = "创建迭代")
    @PostMapping(value = "")
    public ResponseEntity<Sprint> addSprint(
            @RequestParam String name,
            @RequestParam String desc,
            @RequestParam String beginDate,
            @RequestParam String endDate) {
        logger.info("[addSprint] name: " + name + ", desc: " + desc
                + ", beginDate: " + beginDate + ", endDate: " + endDate);
        ResponseEntity<Sprint> responseEntity;
        try {
            Sprint sprint = new Sprint(name, desc, beginDate, endDate);
            sprintService.addSprint(sprint);
            responseEntity = new ResponseEntity<>(sprint, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("addSprint error. errorMsg: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new Sprint(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 编辑迭代
     *
     * @param sprintId  迭代ID
     * @param name      名称
     * @param desc      描述
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "编辑迭代", notes = "编辑迭代")
    @PutMapping(value = "/{sprintId}")
    public ResponseEntity<String> updateSprint(
            @PathVariable String sprintId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String desc,
            @RequestParam(required = false) String beginDate,
            @RequestParam(required = false) String endDate) {
        logger.info("[updateSprint] sprintId: " + sprintId + ", name: " + name
                + ", desc: " + desc + ", beginDate: " + beginDate + ", endDate: " + endDate);
        ResponseEntity<String> responseEntity;
        try {
            Sprint sprint = new Sprint(name, desc, beginDate, endDate);
            sprint.setId(sprintId);
            sprintService.updateSprint(sprint);
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_SPRINT_SUCCESS.getResponseCode(),
                    HttpStatus.OK);
        } catch (Exception e) {
            logger.error("updateSprint error. errorMsg: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_SPRINT_ERROR.getResponseCode(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 删除迭代
     *
     * @param sprintId 迭代ID
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "删除迭代", notes = "删除迭代")
    @DeleteMapping(value = "/{sprintId}")
    public ResponseEntity<String> deleteSprint(@PathVariable String sprintId) {
        logger.info("[deleteSprint] sprintId: " + sprintId);
        ResponseEntity<String> responseEntity;
        try {
            sprintService.deleteSprint(sprintId);
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_SPRINT_SUCCESS.getResponseCode(),
                    HttpStatus.OK);
        } catch (Exception e) {
            logger.error("deleteSprint error. errorMsg: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_SPRINT_ERROR.getResponseCode(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
