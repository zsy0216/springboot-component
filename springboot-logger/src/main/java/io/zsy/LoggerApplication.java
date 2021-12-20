package io.zsy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author: zhangshuaiyin
 * @date: 2021/12/20 20:54
 */
@SpringBootApplication
public class LoggerApplication {
    private static final Logger logger = LoggerFactory.getLogger(LoggerApplication.class);

    public static void main(String[] args) {
        logger.info("测试Logback日志, {}", logger.getClass());
        logger.info("测试Logback日志");

        SpringApplication.run(LoggerApplication.class);
    }
}
