package io.zsy;

import io.zsy.cache.mapper.MenuMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author zhangshuaiyin
 * @date 2021-06-24 13:57
 */
@SpringBootTest
public class MyBatisTests {
    @Resource
    MenuMapper menuMapper;

    @Test
    public void test() {
        menuMapper.selectList().forEach(System.out::println);
    }
}
