package com.agony.project.aop;

import com.agony.project.annotation.AuthCheck;
import com.agony.project.common.ReturnCodeEnum;
import com.agony.project.exception.BusinessException;
import com.agony.project.model.entity.User;
import com.agony.project.service.UserService;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;

/**
 * @author Agony
 * @create 2024/4/1 19:34
 */
@Component
@Aspect
public class AuthInterceptor {

    @Resource
    private UserService userService;


    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        // 获取 anyRoles 和 mustRoles
        List<String> anyRoles = Arrays.stream(authCheck.anyRoles()).filter(StringUtils::isNoneBlank).toList();
        String mustRole = authCheck.mustRole();

        // 获取当前用户
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        User loginUser = userService.getLoginUser(request);
        String userRole = loginUser.getUserRole();

        // 权限验证 拥有任意权限即通过
        if (CollectionUtils.isNotEmpty(anyRoles)) {
            if (!anyRoles.contains(userRole)) {
                throw new BusinessException(ReturnCodeEnum.NO_AUTH_ERROR, "用户无权限");
            }
        }
        // 拥有管理权限即通过
        if (StringUtils.isNotBlank(mustRole)) {
            if (!mustRole.equals(userRole)) {
                throw new BusinessException(ReturnCodeEnum.NO_AUTH_ERROR, "用户无权限");
            }
        }
        return joinPoint.proceed();
    }
}
