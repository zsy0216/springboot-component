package io.zsy.shiro.service;

import io.zsy.shiro.mapper.UserMapper;
import io.zsy.shiro.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: zhangshuaiyin
 * @date: 2021/5/30 20:43
 */
@Service
public class UserServiceImpl implements UserService {
    final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
}
