package io.zsy.h2;

import io.zsy.h2.entity.User;
import io.zsy.h2.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author zhangshuaiyin
 * @date 2021-07-05 09:27
 */
@SpringBootTest
public class SampleTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.isTrue(userList.size() == 5, "验证未通过");
        userList.forEach(System.out::println);
    }

}
