package io.zsy.oss.controller;

import io.zsy.oss.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 默认跳转测试首页
 *
 * @author: zhangshuaiyin
 * @date: 2021/8/7 17:19
 */
@Controller
public class IndexController {

    @Autowired
    private FileService qiNiuFileServiceImpl;

    @GetMapping
    public String index() {
        return "index";
    }

    @ResponseBody
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        return qiNiuFileServiceImpl.upload(file);
    }

    @ResponseBody
    @PostMapping("/remove")
    public String remove(String file) {
        qiNiuFileServiceImpl.delete(file);
        return "删除成功";
    }
}
