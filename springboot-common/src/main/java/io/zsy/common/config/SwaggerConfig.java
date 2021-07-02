package io.zsy.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Swagger 配置类
 *
 * @author zhangshuaiyin
 * @date 2021/5/31 21:58
 */
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {
    /**
     * 创建API应用
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     *
     * @return Docket
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //标注@Api等注解的接口代码路径
                .apis(RequestHandlerSelectors.basePackage("io.zsy.admin.controller"))
                .paths(PathSelectors.any())
                .build()
                .groupName("Typos Admin接口文档V1.0");
    }

    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * 访问地址：http://项目实际地址/swagger-ui.html
     *
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Typos API")
                .description("Typos 后台管理接口文档")
                //服务条款网址
                .termsOfServiceUrl("")
                .version("1.0")
                .contact(new Contact("typos", "http://127.0.0.1/", "594983498@qq.com"))
                .build();
    }
}
