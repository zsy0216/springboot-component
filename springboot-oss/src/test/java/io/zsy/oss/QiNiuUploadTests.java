package io.zsy.oss;

import com.qiniu.util.Auth;
import io.zsy.oss.config.AliYunOssProperties;
import io.zsy.oss.config.QiNiuKodoProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zhangshuaiyin
 * @date 2021-08-09 14:16
 */
@SpringBootTest
public class QiNiuUploadTests {

    @Autowired
    QiNiuKodoProperties qiNiuKodoProperties;
    @Autowired
    AliYunOssProperties aliYunOssProperties;

    public String accessKey = "";
    public String secretKey = "";
    public String bucket = "";

    @Test
    public void testUpload() {
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        System.out.println(upToken);
    }

    @Test
    public void testQiNiuProperties() {
        System.out.println(qiNiuKodoProperties.getAccessKey());
        System.out.println(qiNiuKodoProperties.getSecretKey());
        System.out.println(qiNiuKodoProperties.getBucket());
        System.out.println(qiNiuKodoProperties.getPath());
    }

    @Test
    public void testAliYunProperties() {
        System.out.println(aliYunOssProperties.getAccessKeyId());
        System.out.println(aliYunOssProperties.getAccessKeySecret());
        System.out.println(aliYunOssProperties.getBucketName());
        System.out.println(aliYunOssProperties.getEndpoint());
        System.out.println(aliYunOssProperties.getDomain());
    }

}
