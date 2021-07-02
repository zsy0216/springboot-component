package io.zsy.common.config;

import io.zsy.common.intercepter.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author zhangshuaiyin
 * @date 2021-06-30 15:43
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * 过滤swagger2地址
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 拦截器配置
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // token拦截器 放行feign接口 开放api接口 以及登录接口
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns("/feign/**", "/error", "/company/list", "/user/login")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/doc.html/**", "/open/**")
                .excludePathPatterns("/activity/activity-product");
        super.addInterceptors(registry);
    }
}
