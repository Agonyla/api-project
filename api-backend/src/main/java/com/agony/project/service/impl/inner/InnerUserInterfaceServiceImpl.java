package com.agony.project.service.impl.inner;

import com.agony.project.service.InnerUserInterfaceService;
import com.agony.project.service.UserInterfaceInfoService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author Agony
 * @create 2024/4/11 20:47
 */
@DubboService
public class InnerUserInterfaceServiceImpl implements InnerUserInterfaceService {


    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    /**
     * 调用接口统计
     *
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }
}
