package com.bc.rm.server.mapper;

import com.bc.rm.server.entity.User;

import java.util.List;

/**
 * 用户
 *
 * @author zhou
 */
public interface UserMapper {

    /**
     * 获取用户列表
     *
     * @return 用户列表
     */
    List<User> getUserList();
}
