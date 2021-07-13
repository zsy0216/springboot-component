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
 * 测试 Mybatis Plus 更新操作
 *
 * @author zhangshuaiyin
 * @date 2021-07-13 16:06
 */
@SpringBootTest
public class UpdateTests {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    private User user;

    private final List<User> userList = new ArrayList<>();

    @BeforeEach
    public void initUser() {
        user = new User(1L, "ZhangSan", 26, "test6@zhangshuaiyin.com");
        userList.add(new User(3L, "LiSi", 27, "test7@zhangshuaiyin.com"));
        userList.add(new User(5L, "WangWu", 29, "test8@zhangshuaiyin.com"));
    }

    /* ---------------------- IService 接口更新方法测试 ---------------------- */

    /**
     * Service 接口更新方法，布尔返回值表示是否更新成功
     */
    @Test
    public void testServiceUpdate() {
        // 1. 根据 UpdateWrapper 条件，更新所有满足条件的记录 需要设置sqlset
        // boolean update (Wrapper < T > updateWrapper);
        boolean update = userService.update(new UpdateWrapper<User>().eq("age", 20).setSql("age = 17"));
        userService.list().forEach(System.out::println);

        // 2. 根据 whereWrapper 条件，按照参数中的实体更新满足条件的记录(不更新id)
        // boolean update (T updateEntity, Wrapper < T > whereWrapper);
        boolean updateRecord = userService.update(user, new UpdateWrapper<User>().eq("age", 18));
        userService.list().forEach(System.out::println);

        // 3. 根据 ID 选择修改, 没有该id 返回false 不更新数据
        // boolean updateById (T entity);
        boolean updateById = userService.updateById(user);
        System.out.println(updateById);
        userService.list().forEach(System.out::println);

        // 4. 根据ID 批量更新 参数2: batchSize 每批插入数量 可省略 默认值1000
        // boolean updateBatchById (Collection < T > entityList);
        // boolean updateBatchById (Collection < T > entityList,int batchSize);
        boolean batchById = userService.updateBatchById(userList);
        boolean batchByIdSize = userService.updateBatchById(userList, 2);
        userService.list().forEach(System.out::println);
    }

    /* ---------------------- BaseMapper 接口更新方法测试 ---------------------- */

    /**
     * Mapper 接口更新方法，int 返回值表示更新记录条数
     */
    @Test
    public void testMapperUpdate() {
        // 1. 根据 whereWrapper 条件，更新满足条件的所有记录
        // int update(@Param(Constants.ENTITY) T updateEntity, @Param(Constants.WRAPPER) Wrapper<T> whereWrapper);
        int update = userMapper.update(user, new UpdateWrapper<User>().eq("age", 20));
        System.out.println(update);
        userMapper.selectList(null).forEach(System.out::println);

        // 2. 根据 ID 修改
        // int updateById(@Param(Constants.ENTITY) T entity);
        int updateById = userMapper.updateById(user);
        userMapper.selectList(null).forEach(System.out::println);
    }

}
