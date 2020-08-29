package io.zsy.job.csv2database;

import io.zsy.model.User;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author shuaiyin.zhang
 * @description
 * @date 2020/08/29
 */
@Component
@StepScope
public class Csv2DatabaseItemReader extends FlatFileItemReader<User> {

	@PostConstruct
	public void init() {
		// 由于data.csv文件第一行是标题，因此通过setLinesToSkip方法设置跳过一行
		setLinesToSkip(1);
		// setResource方法配置data.csv文件的位置
		setResource(new ClassPathResource("data.csv"));
		// 通过setLineMapper方法设置每一行的数据信息
		setLineMapper(new DefaultLineMapper<User>() {{
			setLineTokenizer(new DelimitedLineTokenizer() {{
				// setNames方法配置了data.csv文件一共有4列，分别是id、username、以及sex,
				setNames("id", "username", "sex");
				// 配置列与列之间的间隔符（这里是空格）
				setDelimiter(" ");
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper() {{
				setTargetType(User.class);
			}});
		}});
	}
}
