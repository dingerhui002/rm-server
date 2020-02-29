package com.bc.rm.server.controller;

import com.bc.rm.server.entity.User;
import com.bc.rm.server.enums.ResponseMsg;
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

    /**
     * 创建用户
     *
     * @param name  用户名
     * @param phone 手机号
     * @param mail  邮箱
     * @param desc  用户描述
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "创建用户", notes = "创建用户")
    @PostMapping(value = "")
    public ResponseEntity<User> addUser(
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam String mail,
            @RequestParam(required = false) String desc) {
        logger.info("[addUser] name: " + name + ", phone: " + phone
                + ", mail: " + mail + ", desc: " + desc);
        ResponseEntity<User> responseEntity;
        try {
            User user = new User(name, phone, mail, desc);
            userService.addUser(user);
            responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("addUser error. errorMsg: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new User(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 编辑用户
     *
     * @param userId 用户ID
     * @param status 用户状态
     * @param desc   用户描述
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "编辑用户", notes = "编辑用户")
    @PutMapping(value = "/{userId}")
    public ResponseEntity<String> updateUser(
            @PathVariable String userId,
            @RequestParam String status,
            @RequestParam(required = false) String desc) {
        logger.info("[updateUser] userId: " + userId + ", status: " + status + ", desc:" + desc);
        ResponseEntity<String> responseEntity;
        try {
            User user = new User();
            user.setId(userId);
            user.setStatus(status);
            user.setDesc(desc);
            userService.updateUser(user);
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_SUCCESS.getResponseCode(),
                    HttpStatus.OK);
        } catch (Exception e) {
            logger.error("updateUser error. errorMsg: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_ERROR.getResponseCode(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "删除用户", notes = "删除用户")
    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        logger.info("[deleteUser] userId: " + userId);
        ResponseEntity<String> responseEntity;
        try {
            userService.deleteUser(userId);
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_USER_SUCCESS.getResponseCode(),
                    HttpStatus.OK);
        } catch (Exception e) {
            logger.error("deleteUser error. errorMsg: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_USER_ERROR.getResponseCode(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
