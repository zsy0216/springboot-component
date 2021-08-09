package io.zsy.oss;

import com.qiniu.util.Auth;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zhangshuaiyin
 * @date 2021-08-09 14:16
 */
@SpringBootTest
public class QiNiuUploadTests {

    public String accessKey = "3_IaBO18YeMbRnCGEQ_5zRurMtdfZcrLK_W1eFq4";
    public String secretKey = "q0Fb8YYdqNJwtfoMr0n21oeri6xWgSHf-8sCMVY7";
    public String bucket = "boot-oss";

    @Test
    public void testUpload() {
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        System.out.println(upToken);
    }
}
