package io.zsy.param.controller;

import io.zsy.param.request.NameAndAge;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * @author zhangshuaiyin
 * @date 2022/3/18 15:28
 */
@ResponseBody
@RestController
@RequestMapping("common")
public class CommonController {

    /**
     * localhost:8080/get/url/123
     * @param id 123
     */
    @RequestMapping(value = "url/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String testPathParam(@PathVariable("id") String id) {
        return "URL 传参 " + id;
    }

    /**
     * localhost:8080/get/url?name=zs&age=12
     */

    @RequestMapping(value = "url", method = {RequestMethod.GET, RequestMethod.POST})
    public String testEmpty(HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();
        System.out.println(Arrays.toString(paramMap.get("name")));
        System.out.println(Arrays.toString(paramMap.get("age")));

        System.out.println(request.getParameter("name"));
        System.out.println(request.getParameter("age"));

        return "URL 传参: name=" + request.getParameter("name") + ", age=" + request.getParameter("age");
    }

    @RequestMapping(value = "param", method = {RequestMethod.GET, RequestMethod.POST})
    public String testParam(@RequestParam("name") String name, @RequestParam Integer age) {
        return "URL 传参: name=" + name + ", age=" + age;
    }

    @RequestMapping(value = "default", method = {RequestMethod.GET, RequestMethod.POST})
    public String testDefault(String name, Integer age) {
        return "URL 传参: name=" + name + ", age=" + age;
    }

    @RequestMapping(value = "bean", method = {RequestMethod.GET, RequestMethod.POST})
    public String testBean(NameAndAge nameAndAge) {
        return "URL 传参: name=" + nameAndAge.getName() + ", age=" + nameAndAge.getAge();
    }

    /**
     * from 表单传参
     */

    @RequestMapping(value = "form/request", method = {RequestMethod.GET, RequestMethod.POST})
    public String testFormRequest(HttpServletRequest request) {
        return "Form 传参: name=" + request.getParameter("name") + ", age=" + request.getParameter("age");
    }

    @RequestMapping(value = "form/param", method = {RequestMethod.GET, RequestMethod.POST})
    public String testFormParam(@RequestParam("name") String name, @RequestParam Integer age) {
        return "Form 传参: name=" + name + ", age=" + age;
    }

    @RequestMapping(value = "form/default", method = {RequestMethod.GET, RequestMethod.POST})
    public String testFormDefault(String name, Integer age) {
        return "Form 传参: name=" + name + ", age=" + age;
    }

    @RequestMapping(value = "form/bean", method = {RequestMethod.GET, RequestMethod.POST})
    public String testFormBean(NameAndAge nameAndAge) {
        return "Form 传参: name=" + nameAndAge.getName() + ", age=" + nameAndAge.getAge();
    }
}
