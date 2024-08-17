package com.agony.project.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.agony.project.annotation.AuthCheck;
import com.agony.project.common.ResultData;
import com.agony.project.common.ReturnCodeEnum;
import com.agony.project.exception.BusinessException;
import com.agony.project.mapper.UserInterfaceInfoMapper;
import com.agony.project.model.entity.InterfaceInfo;
import com.agony.project.model.entity.UserInterfaceInfo;
import com.agony.project.model.vo.InterfaceInfoVO;
import com.agony.project.service.InterfaceInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Agony
 * @create 2024/4/11 21:44
 */
@RestController
@RequestMapping("/analysis")
@CrossOrigin(value = {"http://localhost:8000/"}, allowCredentials = "true")
public class AnalysisController {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public ResultData<List<InterfaceInfoVO>> listTopInterfaceInfo() {

        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoMapper.listInterfaceInfo(3);
        Map<Long, List<UserInterfaceInfo>> interfaceInfoIdMap = userInterfaceInfoList.stream().collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));

        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", interfaceInfoIdMap.keySet());
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(queryWrapper);
        if (CollectionUtil.isEmpty(interfaceInfoList)) {
            throw new BusinessException(ReturnCodeEnum.SYSTEM_ERROR, "接口查询为空");
        }

        List<InterfaceInfoVO> interfaceInfoVOList = interfaceInfoList.stream().map(interfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
            int totalNum = interfaceInfoIdMap.get(interfaceInfo.getId()).get(0).getTotalNum();
            interfaceInfoVO.setTotalNum(totalNum);
            return interfaceInfoVO;
        }).toList();

        return ResultData.success(interfaceInfoVOList);
    }
}
