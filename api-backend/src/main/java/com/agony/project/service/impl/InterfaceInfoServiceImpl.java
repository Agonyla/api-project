package com.agony.project.service.impl;

import com.agony.project.common.ReturnCodeEnum;
import com.agony.project.exception.BusinessException;
import com.agony.project.mapper.InterfaceInfoMapper;
import com.agony.project.model.entity.InterfaceInfo;
import com.agony.project.service.InterfaceInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @auther agony
 * @description 针对表【interface_info(接口信息)】的数据库操作Service实现
 * @createDate 2024-03-31 21:48:34
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {

    @Override
    public void validUserInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ReturnCodeEnum.PARAMS_ERROR, "接口为null");
        }
        String name = interfaceInfo.getName();
        if (add) {
            if (StringUtils.isAnyBlank(name)) {
                throw new BusinessException(ReturnCodeEnum.PARAMS_ERROR, "接口名字为空");
            }
        }
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ReturnCodeEnum.PARAMS_ERROR, "名称过长");
        }
    }
}




