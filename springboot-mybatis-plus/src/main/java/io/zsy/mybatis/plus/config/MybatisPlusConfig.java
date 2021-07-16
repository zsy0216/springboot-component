package io.zsy.mybatis.plus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

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
