package io.zsy.mybatis.plus.crud;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.zsy.mybatis.plus.entity.User;
import io.zsy.mybatis.plus.mapper.UserMapper;
import io.zsy.mybatis.plus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试 Mybatis Plus 查询操作
 *
 * @author zhangshuaiyin
 * @date 2021-07-13 10:51
 */
@SpringBootTest
public class RetrieveTests {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    /* ---------------------- IService 接口查询方法测试 ---------------------- */

    /**
     * Service 接口中按条件查询一条记录
     */
    @Test
    public void testServiceGet() {
        // 1. 根据 id 查询
        User serviceById = userService.getById(1);
        System.out.println(serviceById);

        // 2. 根据 Wrapper, 查询一条记录。查询不到返回 null
        User serviceDetermineOne = userService.getOne(new QueryWrapper<User>().eq("age", 24));
        System.out.println(serviceDetermineOne);

        // 结果集如果是多个会抛出异常 TooManyResultsException, 随机取一条加上限制条件 wrapper.last("LIMIT 1")
        User serviceOnes = userService.getOne(new QueryWrapper<User>().eq("age", 20).last("LIMIT 1"));
        System.out.println(serviceOnes);

        // 可以在条件后增加是否抛出异常，默认为true，设置为false 不抛出异常 返回随机取一条记录
        User serviceOne = userService.getOne(new QueryWrapper<User>().eq("age", 20), false);
        System.out.println(serviceOne);

        // 3. 根据 Wrapper, 查询一条记录, 结果集多条不会抛出异常，随机取一条返回
        Map<String, Object> serviceMap = userService.getMap(new QueryWrapper<User>().eq("age", 20));
        System.out.println(serviceMap);

        // 4. 根据 Wrapper，查询一条记录, 默认返回第一个字段值，结果集多条不会抛出异常
        Object serviceIdObj = userService.getObj(new QueryWrapper<User>().eq("age", 20), s -> s);
        System.out.println(serviceIdObj);
    }

    /**
     * Service 接口中查询列表
     */
    @Test
    public void testServiceList() {
        // 1. 查询所有
        userService.list().forEach(System.out::println);

        // 2. 按条件查询列表
        userService.list(new QueryWrapper<User>().eq("age", 20)).forEach(System.out::println);

        // 3. 根据 id 批量查询
        userService.listByIds(Arrays.asList(1, 3, 5)).forEach(System.out::println);

        // 4. 根据 columnMap 条件查询列表
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("age", 20);
        userService.listByMap(columnMap).forEach(System.out::println);

        // 5. 查询作为map的所有列表
        List<Map<String, Object>> maps = userService.listMaps();
        maps.forEach(System.out::println);

        // 6. 按条件查询map列表
        List<Map<String, Object>> listMaps = userService.listMaps(new QueryWrapper<User>().eq("age", 20));
        listMaps.forEach(System.out::println);

        // 7. 根据条件查询所有记录, 默认返回第一个字段值对象列表
        userService.listObjs().forEach(System.out::println);
        userService.listObjs(s -> s).forEach(System.out::println);
        userService.listObjs(new QueryWrapper<User>().eq("age", 20)).forEach(System.out::println);
        userService.listObjs(new QueryWrapper<User>().eq("age", 20), s -> s).forEach(System.out::println);
    }

    /**
     * Service 层接口分页查询
     * 需要配置分页插件
     */
    @Test
    public void testServicePage() {
        // 1. 无条件分页查询
        userService.page(new Page<>(1, 2)).getRecords().forEach(System.out::println);

        // 2. 条件分页查询(先按条件查询，然后再分页)
        Page<User> userPage = userService.page(new Page<>(1, 2), new QueryWrapper<User>().eq("age", 20));
        userPage.getRecords().forEach(System.out::println);

        // 3. 无条件分页查询map
        userService.pageMaps(new Page<>(1, 2)).getRecords().forEach(System.out::println);

        // 4. 条件分页查询map
        Page<Map<String, Object>> mapPage = userService.pageMaps(new Page<>(1, 2), new QueryWrapper<User>().eq("age", 20));
        mapPage.getRecords().forEach(System.out::println);
    }

    /**
     * Service 接口计数查询
     */
    @Test
    public void testServiceCount() {
        // 1. 查询总记录数
        int count = userService.count();
        System.out.println(count);

        // 2. 按 Wrapper 条件查询记录数
        int ageCount = userService.count(new QueryWrapper<User>().eq("age", 20));
        System.out.println(ageCount);
    }

    /* ---------------------- BaseMapper 接口相关查询方法测试 ---------------------- */

    /**
     * Mapper 接口查询单条记录
     */
    @Test
    public void testMapperSelectOne() {
        // 1. 根据 ID 查询
        User user = userMapper.selectById(1);
        System.out.println(user);

        // 2. 根据 Wrapper 查询一条记录，结果集有多条会抛出异常: TooManyResultsException, 查询不到返回 null
        User selectOne = userMapper.selectOne(new QueryWrapper<User>().eq("age", 24));
        System.out.println(selectOne);
    }

    /**
     * Mapper 接口查询列表
     */
    @Test
    public void testMapperSelectList() {
        // 1. 根据 id 批量查询
        userMapper.selectBatchIds(Arrays.asList(2, 4)).forEach(System.out::println);

        // 2. 根据 Wrapper 批量查询
        userMapper.selectList(new QueryWrapper<User>().ne("age", 20)).forEach(System.out::println);

        // 3. 根据 columnMap 批量查询
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("age", 20);
        userMapper.selectByMap(columnMap).forEach(System.out::println);

        // 4. 根据 Wrapper 批量查询 Map
        List<Map<String, Object>> maps = userMapper.selectMaps(new QueryWrapper<User>().ne("age", 20));
        maps.forEach(System.out::println);

        // 5. 根据 Wrapper 批量查询 Map, 注意：只返回第一个字段的值
        List<Object> selectObjs = userMapper.selectObjs(new QueryWrapper<User>().eq("age", 20));
        selectObjs.forEach(System.out::println);
    }

    /**
     * Mapper 接口分页查询
     */
    @Test
    public void testMapperSelectPage() {
        // 1. 根据 Wrapper 条件查询后分页
        Page<User> selectPage = userMapper.selectPage(new Page<>(1, 2), new QueryWrapper<User>().eq("age", 20));
        selectPage.getRecords().forEach(System.out::println);

        // 2. 根据 Wrapper 条件查询Map列表后分页
        Page<Map<String, Object>> mapsPage = userMapper.selectMapsPage(new Page<>(1, 2), new QueryWrapper<User>().eq("age", 20));
        mapsPage.getRecords().forEach(System.out::println);
    }

    /**
     * Mapper 接口计数查询
     */
    @Test
    public void testMapperSelectCount() {
        // 根据 Wrapper 条件查询记录数, 不传条件则查询总记录数
        Integer selectCount = userMapper.selectCount(new QueryWrapper<User>().eq("age", 20));
        System.out.println(selectCount);
    }

}
