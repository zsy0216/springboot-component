package io.zsy.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: zhangshuaiyin
 * @date: 2021/5/30 10:12
 */
@Controller
public class HelloController {
    @RequestMapping({"/", "/index"})
    public String toIndex(Model model) {
        model.addAttribute("msg", "World");
        return "index";
    }

    @RequestMapping({"/login"})
    public String toLogin(Model model) {
        return "login";
    }

    @RequestMapping({"/user/add"})
    public String toAdd(Model model) {
        return "user/add";
    }

    @RequestMapping({"/user/update"})
    public String toUpdate(Model model) {
        return "user/update";
    }

    @PostMapping("/login")
    public String login(String username, String password, Model model) {
        // 获取当前用户
        Subject subject = SecurityUtils.getSubject();
        // 封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            // 执行登录操作
            subject.login(token);
            return "index";
        } catch (UnknownAccountException e) {
            model.addAttribute("msg", "用户名错误");
            return "login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "密码错误");
            return "login";
        }
    }
}
