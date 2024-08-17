package com.agony.project.service;

import com.agony.project.model.entity.UserInterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author agony
 * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
 * @createDate 2024-04-08 20:05:50
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    /**
     * 接口校验
     *
     * @param userInterfaceInfo 用户接口信息
     * @param add               是否新增接口
     */
    void validInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);


    /**
     * 调用接口统计
     *
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);
}
