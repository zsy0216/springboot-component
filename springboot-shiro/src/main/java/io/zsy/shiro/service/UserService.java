package io.zsy.shiro.service;

import io.zsy.shiro.model.User;

/**
 * @author: zhangshuaiyin
 * @date: 2021/5/30 20:42
 */
public interface UserService {
    User selectByUsername(String username);
}
