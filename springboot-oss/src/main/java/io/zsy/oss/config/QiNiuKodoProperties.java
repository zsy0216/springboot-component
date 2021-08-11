package io.zsy.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhangshuaiyin
 * @date 2021-08-11 10:02
 */
@Data
@Component
@ConfigurationProperties(prefix = "qiniu.kodo")
public class QiNiuKodoProperties {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String path;
}
