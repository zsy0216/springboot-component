package io.zsy.mybatis.plus.crud;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.zsy.mybatis.plus.entity.User;
import io.zsy.mybatis.plus.mapper.UserMapper;
import io.zsy.mybatis.plus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试 Mybatis Plus 删除操作
 *
 * @author zhangshuaiyin
 * @date 2021-07-13 16:41
 */
@SpringBootTest
public class DeleteTests {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    /* ---------------------- IService 接口删除方法测试 ---------------------- */
    @Test
    public void testServiceRemove() {
        // 1. 根据 Wrapper 条件，删除记录
        // boolean remove(Wrapper<T> queryWrapper);
        boolean remove = userService.remove(new QueryWrapper<User>().eq("age", 18));
        userService.list().forEach(System.out::println);

        // 2. 根据 ID 删除
        // boolean removeById(Serializable id);
        boolean removeById = userService.removeById(3);
        userService.list().forEach(System.out::println);

        // 3. 根据 columnMap 条件，删除记录
        // boolean removeByMap(Map<String, Object> columnMap);
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("name", "Sandy");
        boolean removeByMap = userService.removeByMap(columnMap);
        userService.list().forEach(System.out::println);


        // 4. 删除（根据ID 批量删除）
        // boolean removeByIds(Collection<? extends Serializable> idList);
        boolean removeByIds = userService.removeByIds(Arrays.asList(2, 5));
        userService.list().forEach(System.out::println);
    }


    /* ---------------------- BaseMapper 接口删除方法测试 ---------------------- */
    @Test
    public void testMapperDelete() {
        // 1. 根据 Wrapper 条件，删除记录
        // int delete(@Param(Constants.WRAPPER) Wrapper<T> wrapper);
        int delete = userMapper.delete(new QueryWrapper<User>().eq("age", 18));
        userMapper.selectList(null).forEach(System.out::println);


        // 2. 删除（根据ID 批量删除）
        // int deleteBatchIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList);
        int deleteBatchIds = userMapper.deleteBatchIds(Arrays.asList(2, 5));
        userMapper.selectList(null).forEach(System.out::println);

        // 3. 根据 ID 删除
        // int deleteById(Serializable id);
        int deleteById = userMapper.deleteById(3);
        userMapper.selectList(null).forEach(System.out::println);

        // 4. 根据 columnMap 条件，删除记录
        // int deleteByMap(@Param(Constants.COLUMN_MAP) Map<String, Object> columnMap);
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("name", "Sandy");
        int deleteByMap = userMapper.deleteByMap(columnMap);
        userMapper.selectList(null).forEach(System.out::println);
    }
}
