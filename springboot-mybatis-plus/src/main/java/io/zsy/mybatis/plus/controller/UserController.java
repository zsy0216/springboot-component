package io.zsy.mybatis.plus.controller;

import io.zsy.mybatis.plus.common.controller.BaseController;
import io.zsy.mybatis.plus.entity.User;
import io.zsy.mybatis.plus.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangshuaiyin
 * @date 2021-07-21 14:27
 */
@RestController
@RequestMapping("/users")
public class UserController extends BaseController<User, UserService> {
}
