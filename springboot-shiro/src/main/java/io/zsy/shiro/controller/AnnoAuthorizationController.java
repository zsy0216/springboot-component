package io.zsy.shiro.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注解鉴权测试接口
 *
 * @author: zhangshuaiyin
 * @date: 2021/8/1 19:21
 */
@RestController
public class AnnoAuthorizationController {

    /**
     * 认证之后的用户才能访问
     *
     * @return
     */
    @GetMapping("/testRequiresAuthentication")
    @RequiresAuthentication
    public String testRequiresAuthentication() {
        return "需要认证的用户访问";
    }

    /**
     * 表明该用户需为”guest”用户
     *
     * @return
     */
    @GetMapping("/testRequiresGuest")
    @RequiresAuthentication
    public String testRequiresGuest() {
        return "表明该用户需为”guest”用户";
    }

    /**
     * 需要有指定权限的用户访问
     *
     * @return
     */
    @GetMapping("/testRequiresPermissions")
    @RequiresPermissions(value = {"user:add", "user:update"}, logical = Logical.OR)
    public String testRequiresPermissions() {
        return "需要有指定权限的用户访问";
    }

    /**
     * 需要有指定角色的用户访问
     *
     * @return
     */
    @GetMapping("/testRequiresRoles")
    @RequiresRoles(value = {"root", "admin"}, logical = Logical.OR)
    public String testRequiresRoles() {
        return "需要有指定角色的用户访问";
    }

    /**
     * 用户需为已认证用户或已记住用户
     *
     * @return
     */
    @GetMapping("/testRequiresUser")
    @RequiresUser
    public String testRequiresUser() {
        return "用户需为已认证用户或已记住用户";
    }
}
