package com.agony.project.service;

import com.agony.project.model.entity.InterfaceInfo;

/**
 * @author Agony
 * @create 2024/4/11 20:39
 */
public interface InnerInterfaceService {


    /**
     * 从数据库中查询模拟接口是否存在（请求路径、请求方法、请求参数）
     *
     * @param path
     * @param method
     * @return
     */
    InterfaceInfo getInterfaceInfo(String path, String method);
}
