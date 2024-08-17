package com.agony.apiinterface.config;


import com.agony.apiclient.client.ApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * api 配置类
 *
 * @author Agony
 * @create 2024/4/6 16:14
 */
// 之前的sdk 包可以自动引入了
// @Configuration
@Deprecated
public class ApiClientConfig {

    @Value("${agony.client.access-key}")
    private String accessKey;


    @Value("${agony.client.secret-key}")
    private String secretKey;

    @Bean
    public ApiClient apiClient() {
        return new ApiClient(accessKey, secretKey);
    }
}
