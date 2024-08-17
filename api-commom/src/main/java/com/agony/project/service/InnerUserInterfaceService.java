package com.agony.project.service;

/**
 * @author Agony
 * @create 2024/4/11 20:40
 */
public interface InnerUserInterfaceService {
    /**
     * 调用接口统计
     *
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);
}
