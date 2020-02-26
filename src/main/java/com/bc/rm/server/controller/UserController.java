package com.bc.rm.server.controller;

import com.bc.rm.server.entity.User;
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
 * 用户控制器
 *
 * @author zhou
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    @GetMapping(value = "")
    public ResponseEntity<PageInfo<User>> getUserList(
            @RequestParam(required = false) String name,
            @RequestParam Integer page,
            @RequestParam Integer limit) {
        logger.info("name:" + name + ", page: " + page + ", limit:" + limit);
        PageInfo<User> pageInfo = userService.getUserListByPageInfo(name, page, limit);
        return new ResponseEntity<>(pageInfo, HttpStatus.OK);
    }
}
