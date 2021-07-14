package io.zsy.mybatis.plus.crud;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.zsy.mybatis.plus.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 测试 Mybatis Plus ActiveRecord 模式下增删改查操作
 *
 * @author zhangshuaiyin
 * @date 2021-07-14 09:52
 */
@SpringBootTest
public class ActiveRecordTests {

    @Test
    public void testInsert() {
        User user = new User();
        user.setName("ZhangSan");
        user.setAge(12);
        user.setEmail("594983498@qq.com");
        boolean insert = user.insert();
        user.selectAll().forEach(System.out::println);

        boolean insertOrUpdate = user.insertOrUpdate();
    }

    @Test
    public void testUpdate() {
        User user = new User();
        // 1. 根据主键更新
        user = user.selectById(1);
        user.setName("ZhangSan");
        user.updateById();

        // 2. 更新所有满足条件的数据
        user.update(new UpdateWrapper<User>().eq("age", 20));
        user.selectAll().forEach(System.out::println);
    }

    @Test
    public void testDelete() {
        User user = new User();

        // 1. 根据主键删除
        user = user.selectById(1);
        user.deleteById();
        user.selectAll().forEach(System.out::println);

        // 2. 根据指定的主键删除
        user.deleteById(3);
        user.selectAll().forEach(System.out::println);

        // 3. 删除所有符合条件的数据
        user.delete(new QueryWrapper<User>().eq("age", 20));
        user.selectAll().forEach(System.out::println);
    }

    @Test
    public void testSelect() {
        User user = new User();

        // 1. 查询所有
        user.selectAll().forEach(System.out::println);

        // 2. 根据主键id查询
        user.setId(1L);
        user.selectById();

        user.selectById(1);

        // 3. 根据 Wrapper 条件查询列表
        user.selectList(new QueryWrapper<User>().eq("age", 20)).forEach(System.out::println);

        // 4. 根据 Wrapper 查询单条, 结果集多条不报错，返回查询到的第一条
        user = user.selectOne(new QueryWrapper<User>().eq("age", 20));
        System.out.println(user);

        // 5. 按 Wrapper 条件分页查询(先查询后分页)
        Page<User> selectPage = user.selectPage(new Page<>(1, 2), new QueryWrapper<User>().eq("age", 20));
        selectPage.getRecords().forEach(System.out::println);

        // 6. 按 Wrapper 条件计数查询
        Integer selectCount = user.selectCount(new QueryWrapper<User>().eq("age", 20));
        System.out.println(selectCount);
    }

    @Test
    public void testModelSelectNotes() {
        User user = new User();
        user.selectAll().forEach(System.out::println);
        user.selectAll().forEach(System.out::println);
    }
}
