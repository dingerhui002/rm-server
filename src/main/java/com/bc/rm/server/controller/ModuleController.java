package com.bc.rm.server.controller;

import com.bc.rm.server.entity.Module;
import com.bc.rm.server.service.ModuleService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 模块控制器
 *
 * @author zhou
 */
@RestController
@RequestMapping("/modules")
public class ModuleController {

    private static final Logger logger = LoggerFactory.getLogger(ModuleController.class);

    @Resource
    private ModuleService moduleService;

    /**
     * 新增模块
     *
     * @param name 模块名
     * @param desc 模块描述
     * @return ResponseEntity
     */
    @ApiOperation(value = "新增模块", notes = "新增模块")
    @PostMapping(value = "/")
    public ResponseEntity<String> addModule(
            @RequestParam String name,
            @RequestParam String desc) {
        ResponseEntity<String> responseEntity;
        try {
            Module module = new Module(name, desc);
            moduleService.addModule(module);
            responseEntity = new ResponseEntity<>("", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("addModule error: " + e.getMessage());
            responseEntity = new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}
