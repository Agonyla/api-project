package com.agony.project.service;

import com.agony.project.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @auther agony
 * @description 针对表【interface_info(接口信息)】的数据库操作Service
 * @createDate 2024-03-31 21:48:34
 */
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    /**
     * 接口校验
     *
     * @param interfaceInfo 接口信息
     * @param add           是否新增接口
     */
    void validUserInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
