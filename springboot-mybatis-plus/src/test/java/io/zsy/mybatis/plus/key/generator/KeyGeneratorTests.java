package io.zsy.mybatis.plus.key.generator;

import io.zsy.mybatis.plus.entity.Customer;
import io.zsy.mybatis.plus.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 主键生成策略测试
 *
 * @author zhangshuaiyin
 * @date 2021-07-14 09:15
 */
@SpringBootTest
public class KeyGeneratorTests {

    /*
     * IdType 类型：
     *   1. AUTO(0)：数据库ID自增，数据库必须设置自增
     *   2. NONE(1)：该类型为未设置主键类型(注解里等于跟随全局,全局里约等于 INPUT)
     *   3. INPUT(2)：用户输入ID，该类型可以通过自己注册自动填充插件进行填充
     *   4. ASSIGN_ID(3)：雪花算法生成ID，类型为 number 或 string，插入对象ID必须为空(默认19位)
     *   5. ASSIGN_UUID(4)：UUID不含中划线，类型为 string，插入对象ID必须为空(默认32位)
     */

    /**
     * 标注：@TableId(type = IdType.ASSIGN_ID)
     */
    @Test
    public void testSnowId() {
        User user = new User();
        // user.setId(6L);
        user.setName("ZhangSan");
        user.setAge(12);
        user.setEmail("594983498@qq.com");
        user.insert();
        /*
            默认生成策略 User(id=1415121227104952321, name=ZhangSan, age=12, email=594983498@qq.com)
         */
        user.selectList(null).forEach(System.out::println);
    }

    @Test
    public void testUUID() {
        Customer customer = new Customer();
        customer.setName("ZhangSan");
        customer.setAge(12);
        customer.setEmail("594983498@qq.com");
        customer.insert();
        /*
            默认生成策略 Customer(id=397b882a3614d3cb2b1573586e5fa5eb, name=ZhangSan, age=12, email=594983498@qq.com)
         */
        customer.selectList(null).forEach(System.out::println);
    }

}
