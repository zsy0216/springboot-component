package io.zsy.mybatis.plus.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

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
