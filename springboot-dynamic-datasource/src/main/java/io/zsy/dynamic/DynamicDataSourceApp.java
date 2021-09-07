package io.zsy.dynamic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: zhangshuaiyin
 * @date: 2021/9/7 20:00
 */
@MapperScan("io.zsy.dynamic.mapper")
@SpringBootApplication
public class DynamicDataSourceApp {
    public static void main(String[] args) {
        SpringApplication.run(DynamicDataSourceApp.class, args);
    }
}
