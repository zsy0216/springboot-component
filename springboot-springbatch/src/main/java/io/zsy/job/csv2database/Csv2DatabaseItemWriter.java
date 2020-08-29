package io.zsy.job.csv2database;

import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

/**
 * @author shuaiyin.zhang
 * @description
 * @date 2020/08/29
 */
@Component
public class Csv2DatabaseItemWriter extends JdbcBatchItemWriter {

	@Resource
	DataSource dataSource;

	@PostConstruct
	public void init() {
		// 配置使用的数据源
		setDataSource(dataSource);
		// 配置数据插入SQL，注意占位符的写法是":属性名"
		setSql("insert into user(id,username,sex) values(:id,:username,:sex)");
		// 最后通过BeanPropertyItemSqlParameterSourceProvider实例将实体类的属性和SQL中的占位符一一映射
		setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
	}
}
