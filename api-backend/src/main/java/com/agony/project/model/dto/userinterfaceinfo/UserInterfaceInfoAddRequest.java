package com.agony.project.model.dto.userinterfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Agony
 * @create 2024/4/2 10:55
 */

@Data
public class UserInterfaceInfoAddRequest implements Serializable {

    /**
     * 调用用户 id
     */
    private Long userId;

    /**
     * 接口 id
     */
    private Long interfaceInfoId;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;
}
