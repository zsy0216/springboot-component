package io.zsy.mybatis.plus.crud;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import io.zsy.mybatis.plus.entity.User;
import io.zsy.mybatis.plus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Mybatis Plus 链式查询
 *
 * @author zhangshuaiyin
 * @date 2021-07-13 16:59
 */
@SpringBootTest
public class ChainWrapperTests {
    @Autowired
    UserService userService;

    @Test
    public void testQueryChainWrapper() {
        QueryChainWrapper<User> chainWrapper = userService.query();
        // one() 查询出多条会抛出异常: TooManyResultsException，实际是通过 baseMapper.selectOne() 查找
        User one = chainWrapper.eq("age", 20).eq("name", "Jack").one();
        System.out.println(one);

        // lambda 避免数据库字段的魔法值
        LambdaQueryChainWrapper<User> lambdaQuery = userService.lambdaQuery();
        List<User> users = lambdaQuery.eq(User::getAge, 20).list();
        users.forEach(System.out::println);
    }

    @Test
    public void testUpdateChainWrapper() {
        // 类似 update
    }
}
