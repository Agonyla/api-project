package com.agony.project.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Agony
 * @create 2024/4/2 10:56
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户请求参数
     */
    private String userRequestParams;

    @Serial
    private static final long serialVersionUID = 1L;
}
