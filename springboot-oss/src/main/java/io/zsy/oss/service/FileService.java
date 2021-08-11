package io.zsy.oss.service;

import io.zsy.oss.constant.OssConstant;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 文件服务接口 实现文件上传、列表管理、删除等操作
 *
 * @author zhangshuaiyin
 * @date 2021-08-10 09:13
 */
public interface FileService {

    /**
     * 文件上传
     *
     * @param multipartFile 要上传的文件
     * @return 上传后文件的访问路径
     */
    String upload(MultipartFile multipartFile);

    /**
     * 生成上传后文件的文件名：时间+原文件名
     *
     * @param multipartFile 要上传的文件
     * @return 上传到对象存储服务中的文件名
     */
    default String buildFileName(MultipartFile multipartFile) {
        // 原始文件名
        String originalFilename = multipartFile.getOriginalFilename();
        // 根据当前时间生成文件名前缀
        String filePrefix = LocalDateTime.now().format(DateTimeFormatter.ofPattern(OssConstant.FILE_PREFIX_PATTERN));
        // 上传文件名
        return filePrefix + originalFilename;
    }
}
