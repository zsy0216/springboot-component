package io.zsy.oss.service.impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import io.zsy.oss.config.QiNiuKodoProperties;
import io.zsy.oss.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author zhangshuaiyin
 * @date 2021-08-11 10:08
 */
@Slf4j
@Service
public class QiNiuFileServiceImpl implements FileService {
    @Autowired
    QiNiuKodoProperties qiNiuKodoProperties;

    @Override
    public String upload(MultipartFile multipartFile) {
        // 构造一个带指定 Region 对象的配置类, 华南 - Region.region2(), Region.huanan()
        Configuration cfg = new Configuration(Region.region2());
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成密钥
        Auth auth = Auth.create(qiNiuKodoProperties.getAccessKey(), qiNiuKodoProperties.getSecretKey());
        String upToken = auth.uploadToken(qiNiuKodoProperties.getBucket());
        try {
            /*
             * 参数：
             * 1. inputStream： 要上传文件的输入流
             * 2. key： 上传文件名，默认不指定key的情况下，以文件内容的hash值作为文件名
             * 3. token：上传认证token
             *
             * response: 上传结果
             */
            Response response = uploadManager.put(multipartFile.getInputStream(), buildFileName(multipartFile), upToken, null, null);
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

            // 上传文件的外链地址,通过这个地址可以直接打开图片
            return qiNiuKodoProperties.getPath() + "/" + putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        } catch (IOException e) {
            log.info("获取文件输入流失败");
            e.printStackTrace();
        }
        return "";
    }
}
