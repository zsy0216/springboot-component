# Mybatis-Plus

# 导入依赖+配置+环境略

参考代码 pom.xml、application.yaml 等。

# 主键生成策略

在实体主键上声明 @TableId(type = IdType.ASSIGN_ID) 可以实现主键生成策略

```java
@TableId(type = IdType.ASSIGN_ID)
private Long id;
```

IdType 类型：

1. AUTO(0)：数据库ID自增，数据库必须设置自增
2. NONE(1)：该类型为未设置主键类型(注解里等于跟随全局,全局里约等于 INPUT)

   3. INPUT(2)：用户输入ID，该类型可以通过自己注册自动填充插件进行填充
   4. ASSIGN_ID(3)：雪花算法生成ID，类型为 number 或 string，插入对象ID必须为空(默认19位)
   5. ASSIGN_UUID(4)：UUID不含中划线，类型为 string，插入对象ID必须为空(默认32位)

```java
/**
 * 主键生成策略测试
 *
 * @author zhangshuaiyin
 * @date 2021-07-14 09:15
 */
@SpringBootTest
public class KeyGeneratorTests {
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
```



# 配置文件-分页、自动填充

1. 分页需要定义分页查询；

自动填充注意事项：

1. 自动填充要在填充字段增加 @TableField(fill = FieldFill.Insert) 标注；
2. 配置 MetaObjectHandler bean；
3. 填充字段必须无值，如果属性字段有值则不会覆盖；
4. FieldFill：
   1. DEFAULT：默认不处理
   2. INSERT：插入填充字段
   3. UPDATE：更新填充字段
   4. INSERT_UPDATE：插入和更新填充字段

```java
package io.zsy.mybatis.plus.config;

/**
 * Mybatis Plus 相关配置
 *
 * @author zhangshuaiyin
 * @date 2021-07-13 14:22
 */
@Slf4j
@Configuration
@MapperScan("io.zsy.mybatis.plus.mapper")
public class MybatisPlusConfig {

    /**
     * 新版分页插件，注意选择数据库
     *
     * @return MybatisPlusInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        log.info("Mybatis Plus 分页插件配置完成...");
        return interceptor;
    }

    /**
     * MyBatisPlus自定义字段自动填充处理类 - 实体类中使用 @TableField 注解
     *
     * @description: 注意前端传值时要为null
     * @author: zhangshuaiyin
     * @date: 2021/07/16
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            /**
             * 创建时间
             */
            @Override
            public void insertFill(MetaObject metaObject) {
                log.info(" -------------------- start insert fill ...  --------------------");
                this.fillStrategy(metaObject, "createTime", LocalDateTime.now());
                this.fillStrategy(metaObject, "updateTime", LocalDateTime.now());
            }

            /**
             * 最后一次更新时间
             */
            @Override
            public void updateFill(MetaObject metaObject) {
                log.info(" -------------------- start update fill ...  --------------------");
                // 更新时更新时间字段不为空则无法自动填充，按业务决定是否配置该行
                metaObject.setValue("updateTime", null);
                this.fillStrategy(metaObject, "updateTime", LocalDateTime.now());
            }
        };
    }
}

```

# 代码生成器

```java
package io.zsy.mybatis.plus.util;

/**
 * Mybatis Plus 代码生成器
 *
 * @author zhangshuaiyin
 * @date 2021-07-13 10:08
 */
public class CodeGenerator {
    /**
     * 数据表前缀
     */
    private static final String[] TABLE_PREFIX = {"admin_"};
    /**
     * 要生成的数据库表
     */
    private static final String[] GENERATE_TABLES = {"admin_menu"};

    /**
     * 数据库配置
     */
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://:3306/?useUnicode=true&useSSL=false";

    /**
     * 包配置
     */
    private static final String BASE_PACKAGE = "io.zsy.mybatis.plus";
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    private static final String OUTPUT_DIR = PROJECT_PATH + "/springboot-mybatis-plus/src/main/java";

    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();

        globalConfig.setOutputDir(OUTPUT_DIR)
                .setFileOverride(true)
                // 实体继承 Model 实现 CRUD
                .setActiveRecord(true)
                // XML ResultMap
                .setBaseResultMap(true)
                // XML columnList
                .setBaseColumnList(true)
                .setSwagger2(true)
                .setAuthor("zhangshuaiyin")

                // 自定义文件命名，注意 %s 会自动填充表实体属性
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sController");
        mpg.setGlobalConfig(globalConfig);

        // 数据源配置
        DataSourceConfig dbConfig = new DataSourceConfig();
        dbConfig.setDbType(DbType.MYSQL)
                .setDriverName(DRIVER_NAME)
                .setUsername(USERNAME)
                .setPassword(PASSWORD)
                .setUrl(URL);
        mpg.setDataSource(dbConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 此处可以修改为您的表前缀
        strategy.setTablePrefix(TABLE_PREFIX)
                // 表名生成策略
                .setNaming(NamingStrategy.underline_to_camel)
                // 需要生成的表
                .setInclude(GENERATE_TABLES)
                .setEntityLombokModel(true);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(BASE_PACKAGE)
                .setController("controller")
                .setEntity("entity")
                .setMapper("mapper")
                .setService("service")
                .setServiceImpl("service.impl")
                .setXml("mapper.mapper");
        mpg.setPackageInfo(packageConfig);

        // 执行生成
        mpg.execute();
    }
}

```



# 条件构造器 Wrapper

https://mp.baomidou.com/guide/wrapper.html#abstractwrapper

# CRUD 操作

## Iservice 和 BaseMapper 接口

在 Service 层，都会继承 Iservice 接口，在 Iservice 接口中封装了一些常用的增删改查方法。

```java
public interface UserService extends IService<User> {
}
```

```java
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
```

在 Mapper 层，都会继承 BaseMapper 接口，在 BaseMapper 接口中封装了一些常用的增删改查方法。

```java
public interface UserMapper extends BaseMapper<User> {
}
```

## C-新增

新增方法在以下测试类中完成，包含依赖注入和数据初始化。

```java
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
}
```

### Service 接口新增方法

新增方法测试

```java
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
```

### Mapper 接口新增方法

新增方法测试

```java
@Test
public void testMapperInsert() {
    // 插入一条数据，返回插入条数
    int insert = userMapper.insert(user);
    System.out.println(insert);
    userMapper.selectList(null).forEach(System.out::println);
}
```

## R-查询

查询方法在以下测试类中完成，包含依赖注入。

```java
@SpringBootTest
public class RetrieveTests {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
}
```

### Service 接口查询方法

#### 查询单条

```java
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
```

#### 查询列表

```java
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
```

#### 查询分页

```java
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
```

#### 查询计数

```java
@Test
public void testServiceCount() {
    // 1. 查询总记录数
    int count = userService.count();
    System.out.println(count);

    // 2. 按 Wrapper 条件查询记录数
    int ageCount = userService.count(new QueryWrapper<User>().eq("age", 20));
    System.out.println(ageCount);
}
```

### Mapper 接口查询方法

#### 查询单条

```java
@Test
public void testMapperSelectOne() {
    // 1. 根据 ID 查询
    User user = userMapper.selectById(1);
    System.out.println(user);

    // 2. 根据 Wrapper 查询一条记录，结果集有多条会抛出异常: TooManyResultsException, 查询不到返回 null
    User selectOne = userMapper.selectOne(new QueryWrapper<User>().eq("age", 24));
    System.out.println(selectOne);
}
```

#### 查询列表

```java
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
```

#### 查询分页

```java
@Test
public void testMapperSelectPage() {
    // 1. 根据 Wrapper 条件查询后分页
    Page<User> selectPage = userMapper.selectPage(new Page<>(1, 2), new QueryWrapper<User>().eq("age", 20));
    selectPage.getRecords().forEach(System.out::println);

    // 2. 根据 Wrapper 条件查询Map列表后分页
    Page<Map<String, Object>> mapsPage = userMapper.selectMapsPage(new Page<>(1, 2), new QueryWrapper<User>().eq("age", 20));
    mapsPage.getRecords().forEach(System.out::println);
}
```

#### 查询计数

```java
@Test
public void testMapperSelectCount() {
    // 根据 Wrapper 条件查询记录数, 不传条件则查询总记录数
    Integer selectCount = userMapper.selectCount(new QueryWrapper<User>().eq("age", 20));
    System.out.println(selectCount);
}
```

## U-更新

更新测试在以下测试类中完成，包含依赖注入和测试数据初始化。

```java
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
}
```

### Service 接口更新方法

```java
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
```

### Mapper 接口更新方法

```java
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
```

## D-删除

删除测试在以下测试类中完成，包含依赖注入。

```java
@SpringBootTest
public class DeleteTests {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
}
```

### Service 接口删除方法

```java
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
```

### Mapper 接口删除方法

```java
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
```

## ActiveRecord 模式下增删改查操作

```java
package io.zsy.mybatis.plus.crud;

/**
 * 测试 Mybatis Plus ActiveRecord 模式下增删改查操作
 * 
 * 直接通过实体来实现增删改查操作
 * 1. 实体类需要继承 Model<T>
 * 2. 需要定义 mapper 接口实现 BaseMapper
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
```