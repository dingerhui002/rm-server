package com.bc.rm.server.controller;

import com.bc.rm.server.entity.Sprint;
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
    public ResponseEntity<PageInfo<Sprint>> getUserList(
            @RequestParam Integer page,
            @RequestParam Integer limit) {
        logger.info("page: " + page + ", limit:" + limit);
        PageInfo<Sprint> pageInfo = sprintService.getSprintListByPageInfo(page, limit);
        return new ResponseEntity<>(pageInfo, HttpStatus.OK);
    }
}
