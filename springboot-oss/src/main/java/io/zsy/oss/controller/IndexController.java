package io.zsy.oss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 默认跳转测试首页
 *
 * @author: zhangshuaiyin
 * @date: 2021/8/7 17:19
 */
@Controller
public class IndexController {

    @GetMapping
    public String index() {
        return "index";
    }
}
