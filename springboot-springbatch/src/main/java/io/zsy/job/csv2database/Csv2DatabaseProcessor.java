package io.zsy.job.csv2database;

import io.zsy.model.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * @author shuaiyin.zhang
 * @description 转换性别格式
 * @date 2020/08/29
 */
@Component
public class Csv2DatabaseProcessor implements ItemProcessor<User, User> {
	@Override
	public User process(User user) throws Exception {
		// 数据处理，比如将中文性别设置为M/F
		if ("男".equals(user.getSex())) {
			user.setSex("Male");
		} else {
			user.setSex("Female");
		}
		return user;
	}
}
