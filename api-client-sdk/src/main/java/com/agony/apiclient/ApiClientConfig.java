package com.agony.apiclient;

import com.agony.apiclient.client.ApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Agony
 * @create 2024/4/6 16:47
 */
@Configuration
// @ComponentScan 不需要
@Data
@ConfigurationProperties("agony.client")
public class ApiClientConfig {

    // @Value("${agony.client.access-key}")
    private String accessKey;


    // @Value("${agony.client.secret-key}")
    private String secretKey;

    @Bean
    public ApiClient apiClient() {

        return new ApiClient(accessKey, secretKey);
    }
}
