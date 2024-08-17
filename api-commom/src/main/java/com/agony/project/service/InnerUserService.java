package com.agony.project.service;

import com.agony.project.model.entity.User;

/**
 * @author Agony
 * @create 2024/4/11 20:33
 */
public interface InnerUserService {

    /**
     * 数据库中查是否已分配给用户秘钥（accessKey）
     *
     * @param accessKey
     * @return
     */
    User getInvokeUser(String accessKey);

    String testString(String name);

}
