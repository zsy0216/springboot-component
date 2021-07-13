package io.zsy.mybatis.plus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
