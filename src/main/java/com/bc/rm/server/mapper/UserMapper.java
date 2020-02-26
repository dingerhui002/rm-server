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
     * 获取用户列表
     *
     * @param paramMap 参数map
     * @return 用户列表
     */
    List<User> getUserListByParams(Map<String, String> paramMap);
}
