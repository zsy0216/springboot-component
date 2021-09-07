package io.zsy.dynamic.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * 数据源2配置
 *
 * @author: zhangshuaiyin
 * @date: 2022/9/7 20:02
 */
@Configuration
@MapperScan(basePackages = "is.zsy.dynamic.mapper.ds2", sqlSessionFactoryRef = "sqlSessionFactory2")
public class DataSourceTwoConfig {

    @Autowired
    private DataSource dataSource2;

    @Bean("sqlSessionFactory2")
    public SqlSessionFactory sqlSessionFactory2() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource2);
        // mapper的xml形式文件位置必须要配置，不然将报错：no statement （这种错误也可能是mapper的xml中，namespace与项目的路径不一致导致）
        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:/mapper/ds2/*.xml"));
        return bean.getObject();
    }

    @Bean("sqlSessionTemplate2")
    public SqlSessionTemplate sqlSessionTemplate2() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory2());
    }
}
