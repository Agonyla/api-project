package com.agony.apiinterface;

import com.agony.apiclient.client.ApiClient;
import com.agony.apiclient.model.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Agony
 * @create 2024/4/6 16:33
 */

// 这个 b tmd 一定要加！！！
@SpringBootTest
class MainTest {
    @Test
    public void clientTest() {

        String accessKey = "agony";
        String secretKey = "abcd1234";

        ApiClient apiClient = new ApiClient(accessKey, secretKey);
        String name = apiClient.getNameByGet("jack");
        System.out.println("name = " + name);

        System.out.println("================================");
        System.out.println();

        name = apiClient.getNameByPost("tom");
        System.out.println("name = " + name);

        System.out.println("================================");
        System.out.println();

        User user = new User();
        user.setUsername("marry");
        name = apiClient.getUserNameByPost(user);
        System.out.println("name = " + name);

    }

    // @Resource
    // private ApiClientConfig apiClientConfig;

    @Resource
    private ApiClient apiClient;


    @Test
    public void clientTest2() {

        // ApiClient apiClient = apiClientConfig.apiClient();
        // System.out.println("apiClientConfig = " + apiClientConfig);

        if (apiClient == null) {
            System.out.println("apiClient = " + null);
        }


        String name = apiClient.getNameByGet("jack");
        System.out.println("name = " + name);

        System.out.println("================================");
        System.out.println();

        name = apiClient.getNameByPost("tom");
        System.out.println("name = " + name);

        System.out.println("================================");
        System.out.println();

        User user = new User();
        user.setUsername("marry");
        name = apiClient.getUserNameByPost(user);
        System.out.println("name = " + name);

    }
}