package io.zsy;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shuaiyin.zhang
 * @description
 * @date 2020/08/29
 */
@SpringBootApplication
@EnableBatchProcessing
public class BatchApplication {
	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}
}
