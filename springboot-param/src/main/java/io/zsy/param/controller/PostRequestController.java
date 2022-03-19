package io.zsy.param.controller;

import io.zsy.param.request.NameAndAge;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangshuaiyin
 * @date: 2021/12/23 21:02
 */
@ResponseBody
@RestController
@RequestMapping("post")
public class PostRequestController {

    @RequestMapping(value = "body/bean", method = {RequestMethod.POST})
    public String testBodyBean(@RequestBody NameAndAge nameAndAge) {
        return "Body 传参: name=" + nameAndAge.getName() + ", age=" + nameAndAge.getAge();
    }
}
