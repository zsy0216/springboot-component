package io.zsy.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import io.zsy.oss.config.AliYunOssProperties;
import io.zsy.oss.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author zhangshuaiyin
 * @date 2021-08-11 14:38
 */
@Slf4j
@Service
public class AliYunFileServiceImpl implements FileService {

    @Autowired
    AliYunOssProperties ossProperties;

    @Override
    public String upload(MultipartFile multipartFile) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());

        try {
            String fileName = buildFileName(multipartFile);
            PutObjectResult result = ossClient.putObject(ossProperties.getBucketName(), fileName, multipartFile.getInputStream());
            // TODO 将上传成功的文件添加到数据库中保存, 考虑使用md5值做主键，判断是否上传重复
            return ossProperties.getDomain() + "/" + fileName;
        } catch (OSSException ex) {
            log.info("上传文件失败");
            System.err.println(ex.getMessage());
        } catch (IOException e) {
            log.info("获取文件输入流失败");
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }

    @Override
    public void delete(String fileName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());

        // 删除文件或目录。如果要删除目录，目录必须为空。
        ossClient.deleteObject(ossProperties.getBucketName(), fileName);
    }
}
