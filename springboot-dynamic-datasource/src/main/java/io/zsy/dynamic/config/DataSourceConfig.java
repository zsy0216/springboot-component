package io.zsy.dynamic.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 数据源配置
 *
 * @author: zhangshuaiyin
 * @date: 2021/9/7 19:57
 */
@Configuration
public class DataSourceConfig {

    /**
     * 数据源1 配置
     *
     * @return
     */
    @Bean("dataSource1")
    @ConfigurationProperties(prefix = "spring.datasource.ds1")
    public DataSource dataSource1() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 数据源2 配置
     *
     * @return
     */
    @Bean("dataSource2")
    @ConfigurationProperties(prefix = "spring.datasource.ds2")
    public DataSource dataSource2() {
        return DruidDataSourceBuilder.create().build();
    }
}
