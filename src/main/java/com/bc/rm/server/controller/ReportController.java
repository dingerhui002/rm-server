package com.bc.rm.server.controller;

import com.bc.rm.server.service.BackLogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 报表控制器
 *
 * @author zhou
 */
@RestController
@RequestMapping("/reports")
public class ReportController {

    @Resource
    private BackLogService backLogService;
}
