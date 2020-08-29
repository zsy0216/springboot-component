package io.zsy.job;

import io.zsy.job.csv2database.Csv2DatabaseItemReader;
import io.zsy.job.csv2database.Csv2DatabaseItemWriter;
import io.zsy.job.csv2database.Csv2DatabaseProcessor;
import io.zsy.model.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author shuaiyin.zhang
 * @description 读取 CSV 文件内容到数据库的 JOB
 * @date 2020/08/29
 */
@Configuration
public class Csv2DatabaseJobConfig {

	@Resource
	JobBuilderFactory jobBuilderFactory;
	@Resource
	StepBuilderFactory stepBuilderFactory;

	@Resource
	Csv2DatabaseItemReader itemReader;
	@Resource
	Csv2DatabaseProcessor itemProcessor;
	@Resource
	Csv2DatabaseItemWriter itemWriter;

	// 配置一个ItemReader，即数据的读取逻辑
	/*@Bean
	@StepScope
	FlatFileItemReader<User> itemReader() {
		// FlatFileItemReader 是一个加载普通文件的 ItemReader
		FlatFileItemReader<User> reader = new FlatFileItemReader<>();
		// 由于data.csv文件第一行是标题，因此通过setLinesToSkip方法设置跳过一行
		reader.setLinesToSkip(1);
		// setResource方法配置data.csv文件的位置
		reader.setResource(new ClassPathResource("data.csv"));
		// 通过setLineMapper方法设置每一行的数据信息
		reader.setLineMapper(new DefaultLineMapper<User>(){{
			setLineTokenizer(new DelimitedLineTokenizer(){{
				// setNames方法配置了data.csv文件一共有4列，分别是id、username、以及sex,
				setNames("id","username","sex");
				// 配置列与列之间的间隔符（这里是空格）
				setDelimiter(" ");
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper(){{
				setTargetType(User.class);
			}});
		}});
		return reader;
	}*/

	// 配置ItemWriter，即数据的写出逻辑
	/*@Bean
	JdbcBatchItemWriter itemWriter() {
		// 使用的JdbcBatchltemWriter则是通过JDBC将数据写出到一个关系型数据库中。
		JdbcBatchItemWriter writer = new JdbcBatchItemWriter();
		// 配置使用的数据源
		writer.setDataSource(dataSource);
		// 配置数据插入SQL，注意占位符的写法是":属性名"
		writer.setSql("insert into user(id,username,sex) " +
				"values(:id,:username,:sex)");
		// 最后通过BeanPropertyItemSqlParameterSourceProvider实例将实体类的属性和SQL中的占位符一一映射
		writer.setItemSqlParameterSourceProvider(
				new BeanPropertyItemSqlParameterSourceProvider<>());
		return writer;
	}*/

	@Bean
	Step csv2DatabaseStep() {
		return stepBuilderFactory.get("csv2DatabaseStep")
				.<User, User>chunk(2)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(itemWriter)
				.build();
	}

	@Bean
	Job csv2DatabaseJob() {
		return jobBuilderFactory.get("csv2DatabaseJobWithProcess")
				.start(csv2DatabaseStep())
				.build();
	}
}
