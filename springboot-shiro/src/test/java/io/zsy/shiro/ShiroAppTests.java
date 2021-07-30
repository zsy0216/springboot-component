package io.zsy.shiro;

import io.zsy.shiro.model.SysUser;
import io.zsy.shiro.service.SysUserService;
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
    SysUserService userService;

    @Test
    public void context() {
        SysUser admin = userService.selectByUsername("root");
        System.out.println(admin.getPassword());
    }

    @Test
    public void testRealmAuthentication() {

    }
}
