package io.zsy.shiro;

import io.zsy.shiro.model.User;
import io.zsy.shiro.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: zhangshuaiyin
 * @date: 2021/5/30 20:57
 */
@SpringBootTest
public class ShiroAppTests {
    @Autowired
    UserService userService;

    @Test
    public void context() {
        User admin = userService.selectByUsername("admin");
        System.out.println(admin.getPassword());
    }
}
