package com.agony.project.service.impl;

import com.agony.project.service.UserInterfaceInfoService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Agony
 * @create 2024/4/9 10:22
 */
@SpringBootTest
class UserInterfaceInfoServiceImplTest {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Test
    public void invokeCount() {

        boolean b = userInterfaceInfoService.invokeCount(1L, 1L);

        System.out.println("test success");
    }
}