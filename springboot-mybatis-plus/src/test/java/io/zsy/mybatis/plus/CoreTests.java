package io.zsy.mybatis.plus;

import io.zsy.mybatis.plus.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zhangshuaiyin
 * @date 2021-07-13 10:23
 */
@SpringBootTest
public class CoreTests {

    @Test
    public void context() {
        User user = new User();
        user.selectAll().forEach(System.out::println);
    }
}
