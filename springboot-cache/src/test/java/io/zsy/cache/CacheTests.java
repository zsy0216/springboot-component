package io.zsy.cache;

import io.zsy.cache.mapper.MenuMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author zhangshuaiyin
 * @date 2021-06-24 13:57
 */
@SpringBootTest
public class CacheTests {
    @Resource
    MenuMapper menuMapper;

    @Test
    public void test() {
        System.out.println("select database -1, " +  menuMapper.selectList().size());
        menuMapper.selectList();
        System.out.println("select database -2, " +  menuMapper.selectList().size());
    }
}
