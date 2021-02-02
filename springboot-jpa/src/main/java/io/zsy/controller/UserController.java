package io.zsy.controller;

import io.zsy.model.User;
import io.zsy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: zhangshuaiyin
 * @date: 2021/2/2 15:40
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public String insertUser(@RequestBody User user) {

        if (userRepository.save(user) != null) {
            return "新增成功";
        }
        return "新增失败";
    }

    @GetMapping
    public Iterable<User> listUser(){
        return userRepository.findAll();
    }
}
