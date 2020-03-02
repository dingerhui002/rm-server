package com.bc.rm.server.controller;

import com.bc.rm.server.entity.*;
import com.bc.rm.server.service.ModuleService;
import com.bc.rm.server.service.SprintService;
import com.bc.rm.server.service.StatusService;
import com.bc.rm.server.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 可选项
 *
 * @author zhou
 */
@RestController
@RequestMapping("/options")
public class OptionsController {
    private static final Logger logger = LoggerFactory.getLogger(OptionsController.class);

    @Resource
    private UserService userService;

    @Resource
    private ModuleService moduleService;

    @Resource
    private SprintService sprintService;

    @Resource
    private StatusService statusService;

    @ApiOperation(value = "获取可选项", notes = "获取可选项")
    @GetMapping(value = "")
    public ResponseEntity<Options> getOptions() {
        logger.info("[getOptions] ");
        Options options = new Options();

        // 当前处理人可选项
        List<User> currentUserList = userService.getUserList();
        options.setCurrentUserList(currentUserList);

        // 模块可选项
        List<Module> moduleList = moduleService.getModuleList();
        options.setModuleList(moduleList);

        // 迭代可选项
        List<Sprint> sprintList = sprintService.getSprintList();
        options.setSprintList(sprintList);

        // 状态可选项
        List<Status> statusList = statusService.getStatusList();
        options.setStatusList(statusList);

        return new ResponseEntity<>(options, HttpStatus.OK);
    }
}
