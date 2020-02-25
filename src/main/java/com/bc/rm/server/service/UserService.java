package com.bc.rm.server.service;

import com.bc.rm.server.entity.User;
import com.github.pagehelper.PageInfo;

/**
 * 用户
 *
 * @author zhou
 */
public interface UserService {
    /**
     * 获取用户分页列表
     *
     * @param pageNum  当前分页
     * @param pageSize 每个分页大小
     * @return 用户分页列表
     */
    PageInfo<User> getUserListByPageInfo(int pageNum, int pageSize);
}
