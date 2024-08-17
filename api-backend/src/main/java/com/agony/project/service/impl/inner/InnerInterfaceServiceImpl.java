package com.agony.project.service.impl.inner;

import com.agony.project.common.ReturnCodeEnum;
import com.agony.project.exception.BusinessException;
import com.agony.project.mapper.InterfaceInfoMapper;
import com.agony.project.model.entity.InterfaceInfo;
import com.agony.project.service.InnerInterfaceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author Agony
 * @create 2024/4/11 20:47
 */
@DubboService
public class InnerInterfaceServiceImpl implements InnerInterfaceService {


    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    /**
     * 从数据库中查询模拟接口是否存在（请求路径、请求方法、请求参数）
     *
     * @param path
     * @param method
     * @return
     */
    @Override
    public InterfaceInfo getInterfaceInfo(String path, String method) {

        if (StringUtils.isAnyBlank(path, method)) {
            throw new BusinessException(ReturnCodeEnum.PARAMS_ERROR, "参数为空");
        }

        String url = "http://localhost:8123" + path;

        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", url);
        queryWrapper.eq("method", method);

        return interfaceInfoMapper.selectOne(queryWrapper);

    }
}
