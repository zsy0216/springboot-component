package io.zsy.mybatis.plus.field.fill;

import io.zsy.mybatis.plus.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Mybatis Plus 自动填充测试
 *
 * 1. 需要填充的字段加上 @TableField
 * 2. 实现 MetaObjectHandler 接口
 * 3. 操作该字段时传值为null
 *
 * @author zhangshuaiyin
 * @date 2021-07-16 08:58
 */
@SpringBootTest
public class FiledFillTests {

    private Customer customer;

    @BeforeEach
    public void init() {
        customer = new Customer();
        customer.setName("zhangsan");
        customer.setAge(12);
        customer.setEmail("594983498@qq.com");
    }

    @Test
    public void testFieldFill() throws InterruptedException {
        customer.insert();
        customer.selectAll().forEach(System.out::println);
        Thread.sleep(2000);

        // customer = customer.selectById(1);
        customer.setName("Zhang San");
        // customer.setUpdateTime(null);
        customer.updateById();
        customer.selectAll().forEach(System.out::println);
    }
}
