package com.bc.rm.server.mapper;

import com.bc.rm.server.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 用户
 *
 * @author zhou
 */
public interface UserMapper {

    /**
     * 新增用户
     *
     * @param user 用户
     */
    void addUser(User user);

    /**
     * 获取用户列表
     *
     * @return 用户列表
     */
    List<User> getUserList();

    /**
     * 获取用户列表
     *
     * @param paramMap 参数map
     * @return 用户列表
     */
    List<User> getUserListByParams(Map<String, String> paramMap);

    /**
     * 修改用户
     *
     * @param user 用户
     */
    void updateUser(User user);

    /**
     * 删除用户
     *
     * @param userId 用户ID
     */
    void deleteUser(String userId);
}
