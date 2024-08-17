package com.agony.project.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <a href="https://doc.xiaominfo.com/knife4j/documentation/get_start.html">Knife4j 接口文档配置</a>
 *
 * @auther Agony
 * @create 2024/1/2 10:34
 * @Version 1.0
 */
@Configuration
public class knife4jConfig {


    // @Bean
    // public GroupedOpenApi UserApi() {
    //     return GroupedOpenApi.builder().group("用户模块").pathsToMatch("/user/**").build();
    // }
    //
    // @Bean
    // public GroupedOpenApi InterfaceInfoApi() {
    //     return GroupedOpenApi.builder().group("接口模块").pathsToMatch("/interfaceInfo/**").build();
    // }

    @Bean
    public OpenAPI docsOpenApi() {
        return new OpenAPI()
                .info(new Info().title("api-backend")
                        .description("通用设计rest")
                        .version("v1.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("www.baidu.com")
                        .url("https://yiyan.baidu.com/"));
    }
}
