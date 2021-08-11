package io.zsy.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhangshuaiyin
 * @date 2021-08-11 14:34
 */
@Data
@Component
@ConfigurationProperties("aliyun.oss")
public class AliYunOssProperties {
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String endpoint;
    private String domain;
}
