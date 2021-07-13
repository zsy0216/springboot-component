package io.zsy.mybatis.plus.crud;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.zsy.mybatis.plus.entity.User;
import io.zsy.mybatis.plus.mapper.UserMapper;
import io.zsy.mybatis.plus.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试 Mybatis Plus 新增操作
 *
 * @author zhangshuaiyin
 * @date 2021-07-13 14:57
 */
@SpringBootTest
public class CreateTests {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    private User user;

    private final List<User> userList = new ArrayList<>();

    @BeforeEach
    public void initUser() {
        user = new User(6L, "ZhangSan", 26, "test6@zhangshuaiyin.com");
        userList.add(new User(7L, "LiSi", 27, "test7@zhangshuaiyin.com"));
        userList.add(new User(8L, "WangWu", 29, "test8@zhangshuaiyin.com"));
    }

    /* ---------------------- IService 接口新增方法测试 ---------------------- */
    @Test
    public void testServiceSave() {
        // 1. 插入一条记录、选择字段策略插入
        boolean save = userService.save(user);
        userService.list().forEach(System.out::println);

        // 2. 批量插入 参数2: batchSize 每批插入数量 可省略 默认值1000
        // boolean saveBatch = userService.saveBatch(userList);
        boolean saveBatchSize = userService.saveBatch(userList, 2);
        userService.list().forEach(System.out::println);
    }

    @Test
    public void testServiceSaveOrUpdate() {
        // 1. 根据主键判断是否存在，存在更新记录，否则插入一条记录
        boolean saveOrUpdate = userService.saveOrUpdate(user);
        userService.list().forEach(System.out::println);

        // 2. 根据 Wrapper 条件更新，条件不成立则按照 saveOrUpdate(T) 执行
        // 执行步骤：查找是否存在满足条件的记录，存在则更新查找到的所有记录，否则按照主键判断插入或更新
        // 即，查找是否有 age=20 的 User 数据，存在则更新这些数据，不存在则根据主键判断插入或更新
        userService.saveOrUpdate(user, new UpdateWrapper<User>().eq("age", 20));
        userService.list().forEach(System.out::println);

        // 3. 批量修改插入 参数2: batchSize 每批插入数量 可省略 默认值1000
        // userService.saveOrUpdateBatch(userList);
        userService.saveOrUpdateBatch(userList, 2);
        userService.list().forEach(System.out::println);
    }

    /* ---------------------- BaseMapper 接口新增方法测试 ---------------------- */

    @Test
    public void testMapperInsert() {
        // 插入一条数据，返回插入条数
        int insert = userMapper.insert(user);
        System.out.println(insert);
        userMapper.selectList(null).forEach(System.out::println);
    }
}
