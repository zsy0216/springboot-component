package io.zsy.job;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author shuaiyin.zhang
 * @description
 * @date 2020/08/29
 */
@SpringBootTest
public class Csv2DataBaseJobTest {

	@Resource
	JobLauncher jobLauncher;

	@Resource
	Job csv2DatabaseJobWithProcess;

	@Resource
	DataSource dataSource;

	@Test
	public void testCsv2DatabaseJob() {
		try {
			JobParameters jobParameters = new JobParametersBuilder().toJobParameters();
			// 通过调用 JobLauncher 中的 run 方法启动一个批处理
			jobLauncher.run(csv2DatabaseJobWithProcess, jobParameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDataSource() throws SQLException {
		System.out.println(dataSource.getConnection());
	}
}
