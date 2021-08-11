package io.zsy.oss.controller;

import io.zsy.oss.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhangshuaiyin
 * @date 2021-08-11 15:00
 */
@Controller
@RequestMapping("oss")
public class AliYunFileController {
    @Autowired
    private FileService aliYunFileServiceImpl;

    @ResponseBody
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        return aliYunFileServiceImpl.upload(file);
    }

    @ResponseBody
    @PostMapping("/remove")
    public String remove(String file) {
        aliYunFileServiceImpl.delete(file);
        return "删除成功";
    }
}
