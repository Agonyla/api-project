package com.agony.project.controller;

import cn.hutool.json.JSONUtil;
import com.agony.apiclient.client.ApiClient;
import com.agony.project.annotation.AuthCheck;
import com.agony.project.common.DeleteRequest;
import com.agony.project.common.IdRequest;
import com.agony.project.common.ResultData;
import com.agony.project.common.ReturnCodeEnum;
import com.agony.project.constant.CommonConstant;
import com.agony.project.exception.BusinessException;
import com.agony.project.model.dto.interfaceInfo.InterfaceInfoAddRequest;
import com.agony.project.model.dto.interfaceInfo.InterfaceInfoInvokeRequest;
import com.agony.project.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.agony.project.model.dto.interfaceInfo.InterfaceInfoUpdateRequest;
import com.agony.project.model.entity.InterfaceInfo;
import com.agony.project.model.entity.User;
import com.agony.project.model.enums.InterfaceInfoStatusEnum;
import com.agony.project.service.InterfaceInfoService;
import com.agony.project.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 接口管理
 *
 * @author Agony
 * @create 2024/4/2 10:58
 */
@RestController
@Slf4j
@RequestMapping("/interfaceInfo")
@Tag(name = "InterfaceInfoController", description = "接口信息模块")
@CrossOrigin(value = {"http://localhost:8000/"}, allowCredentials = "true")
public class InterfaceInfoController {
    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    @Resource
    private ApiClient apiClient;


    // region 增删改查

    /**
     * 创建
     *
     * @param interfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public ResultData<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ReturnCodeEnum.PARAMS_ERROR, "新增请求为空");
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddRequest, interfaceInfo);
        // 校验
        interfaceInfoService.validUserInterfaceInfo(interfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserId(loginUser.getId());
        boolean result = interfaceInfoService.save(interfaceInfo);
        if (!result) {
            throw new BusinessException(ReturnCodeEnum.OPERATION_ERROR);
        }
        long newInterfaceInfoId = interfaceInfo.getId();
        return ResultData.success(newInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public ResultData<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ReturnCodeEnum.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ReturnCodeEnum.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ReturnCodeEnum.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoService.removeById(id);
        return ResultData.success(b);
    }

    /**
     * 更新
     *
     * @param interfaceInfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public ResultData<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest,
                                                   HttpServletRequest request) {
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ReturnCodeEnum.PARAMS_ERROR, "更新请求为空");
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest, interfaceInfo);
        // 参数校验
        interfaceInfoService.validUserInterfaceInfo(interfaceInfo, false);
        User user = userService.getLoginUser(request);
        long id = interfaceInfoUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ReturnCodeEnum.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ReturnCodeEnum.NO_AUTH_ERROR);
        }
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultData.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    @Operation(summary = "根据id获取接口")
    public ResultData<InterfaceInfo> getInterfaceInfoById(@RequestParam("id") Long id) {
        if (id <= 0) {
            throw new BusinessException(ReturnCodeEnum.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        log.info("接口信息: {}", interfaceInfo);

        return ResultData.success(interfaceInfo);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    @Operation(summary = "获取接口列表")
    public ResultData<List<InterfaceInfo>> listInterfaceInfo(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        if (interfaceInfoQueryRequest != null) {
            BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(queryWrapper);
        return ResultData.success(interfaceInfoList);
    }

    /**
     * 分页获取列表
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public ResultData<Page<InterfaceInfo>> listInterfaceInfoByPage(InterfaceInfoQueryRequest interfaceInfoQueryRequest, HttpServletRequest request) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ReturnCodeEnum.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();
        String description = interfaceInfoQuery.getDescription();
        // description 需支持模糊搜索
        interfaceInfoQuery.setDescription(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ReturnCodeEnum.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        queryWrapper.like(StringUtils.isNotBlank(description), "description", description);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultData.success(interfaceInfoPage);
    }

    // endregion


    /**
     * 发布接口
     *
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("/online")
    public ResultData<Boolean> onlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                   HttpServletRequest request) {
        // 非空校验
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ReturnCodeEnum.PARAMS_ERROR, "更新请求为空");
        }
        Long id = idRequest.getId();
        // 1. 查询接口是否存在
        InterfaceInfo queryInterfaceInfo = interfaceInfoService.getById(id);
        if (queryInterfaceInfo == null) {
            throw new BusinessException(ReturnCodeEnum.NOT_FOUND_ERROR, "接口不存在");
        }
        // 2.判断该接口是否可以调用 -> 通过调用一下 apiClient 的放法
        com.agony.apiclient.model.User user = new com.agony.apiclient.model.User();
        user.setUsername("testName");
        String testName = apiClient.getUserNameByPost(user);
        if (StringUtils.isAnyBlank(testName)) {
            throw new BusinessException(ReturnCodeEnum.SYSTEM_ERROR, "接口验证失败");
        }
        // 3. 修改接口状态
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.ONLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultData.success(result);
    }


    /**
     * 下限接口
     *
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("/offline")
    public ResultData<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                    HttpServletRequest request) {
        // 非空校验
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ReturnCodeEnum.PARAMS_ERROR, "更新请求为空");
        }
        Long id = idRequest.getId();
        // 1. 查询接口是否存在
        InterfaceInfo queryInterfaceInfo = interfaceInfoService.getById(id);
        if (queryInterfaceInfo == null) {
            throw new BusinessException(ReturnCodeEnum.NOT_FOUND_ERROR, "接口不存在");
        }
        // 2. 修改接口状态
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.OFFLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultData.success(result);

    }


    /**
     * 测试调用
     *
     * @param interfaceInfoInvokeRequest
     * @param request
     * @return
     */
    @PostMapping("/invoke")
    @Operation(summary = "测试调用")
    public ResultData<Object> offlineInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeRequest,
                                                   HttpServletRequest request) {
        // 非空校验
        if (interfaceInfoInvokeRequest == null || interfaceInfoInvokeRequest.getId() <= 0) {
            throw new BusinessException(ReturnCodeEnum.PARAMS_ERROR, "更新请求为空");
        }
        Long id = interfaceInfoInvokeRequest.getId();
        String userRequestParams = interfaceInfoInvokeRequest.getUserRequestParams();
        // 1. 查询接口是否存在
        InterfaceInfo queryInterfaceInfo = interfaceInfoService.getById(id);
        if (queryInterfaceInfo == null) {
            throw new BusinessException(ReturnCodeEnum.NOT_FOUND_ERROR, "接口不存在");
        }
        // 2. 查询接口是否关闭
        if (!(queryInterfaceInfo.getStatus() == InterfaceInfoStatusEnum.ONLINE.getValue())) {
            throw new BusinessException(ReturnCodeEnum.PARAMS_ERROR, "接口已关闭");
        }

        // 3. 模拟调用
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        com.agony.apiclient.model.User user = JSONUtil.toBean(userRequestParams, com.agony.apiclient.model.User.class);

        System.out.println("user = " + user);
        ApiClient tempClient = new ApiClient(accessKey, secretKey);
        String name = tempClient.getUserNameByPost(user);
        return ResultData.success(name);

    }


}
