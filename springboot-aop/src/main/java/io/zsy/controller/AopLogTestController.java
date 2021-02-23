package io.zsy.controller;

import io.zsy.annotation.WebLogAnnotation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangshuaiyin
 * @date: 2021/2/23 14:46
 */
@RestController
public class AopLogTestController {
    @GetMapping("/log")
    @WebLogAnnotation(description = "AOP 统一日志打印测试")
    public String aroundLog(String msg) {
        return msg;
    }
}
