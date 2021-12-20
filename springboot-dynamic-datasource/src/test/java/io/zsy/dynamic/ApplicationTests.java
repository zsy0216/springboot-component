package io.zsy.dynamic;

import io.zsy.dynamic.mapper.test.TestMapper;
import io.zsy.dynamic.mapper.ds2.DemoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: zhangshuaiyin
 * @date: 2021/9/7 20:11
 */
@SpringBootTest
public class ApplicationTests {

    @Autowired
    TestMapper testMapper;

    @Autowired
    DemoMapper demoMapper;

    @Test
    public void demoDataSource() {
        System.out.println(demoMapper.selectByPrimaryKey(1));
    }

    @Test
    public void testDataSource(){
        System.out.println(testMapper.selectByPrimaryKey(1));
    }
}
