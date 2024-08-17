package com.agony.project.service.impl.inner;

import com.agony.project.common.ReturnCodeEnum;
import com.agony.project.exception.BusinessException;
import com.agony.project.mapper.UserMapper;
import com.agony.project.model.entity.User;
import com.agony.project.service.InnerUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author Agony
 * @create 2024/4/11 20:46
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService {


    @Resource
    private UserMapper userMapper;

    /**
     * 数据库中查是否已分配给用户秘钥（accessKey）
     *
     * @param accessKey
     * @return
     */
    @Override
    public User getInvokeUser(String accessKey) {

        if (StringUtils.isAnyBlank(accessKey)) {
            throw new BusinessException(ReturnCodeEnum.PARAMS_ERROR, "参数为空");
        }

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("accessKey", accessKey);
        return userMapper.selectOne(userQueryWrapper);

    }

    @Override
    public String testString(String name) {
        return "test name: " + name;
    }


}
