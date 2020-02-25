package com.bc.rm.server.service.impl;

import com.bc.rm.server.entity.User;
import com.bc.rm.server.mapper.UserMapper;
import com.bc.rm.server.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户
 *
 * @author zhou
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 获取用户分页列表
     *
     * @param pageNum  当前分页
     * @param pageSize 每个分页大小
     * @return 用户分页列表
     */
    @Override
    public PageInfo<User> getUserListByPageInfo(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.getUserList();
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }
}
