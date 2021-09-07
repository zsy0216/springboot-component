package io.zsy.dynamic;

import io.zsy.dynamic.mapper.ds1.TestMapper;
import io.zsy.dynamic.mapper.ds2.DemoMapper;
import io.zsy.dynamic.model.ds2.Demo;
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
    public void testDynamicDataSource() {
        System.out.println(testMapper.selectByPrimaryKey(1));
        Demo demo = new Demo();
        demo.setId(3);
        demo.setName("wangwu");
        demoMapper.insert(demo);
    }

}
