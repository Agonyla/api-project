package com.agony.apiinterface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Agony
 * @create 2024/4/3 21:27
 */


// 适用于springboot2 => 如果使用的是client的 ApiClientConfig 要在这里显式的引入
// @Import(ApiClientConfig.class)

// springboot3 => 在resources目录下建立META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
// 每个单独的全类名一行
@SpringBootApplication
public class Main8123 {
    public static void main(String[] args) {
        SpringApplication.run(Main8123.class, args);
    }
}

