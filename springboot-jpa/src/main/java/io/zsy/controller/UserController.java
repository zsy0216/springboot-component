package io.zsy.controller;

import io.zsy.model.User;
import io.zsy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Iterable<User> listUser() {
        return userRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public String removeUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "删除成功";
    }

    @GetMapping("/user")
    public String findUserByNameAndEmail(String name, String email) {
        return userRepository.findUserByNameAndEmail(name, email).toString();
    }
}
