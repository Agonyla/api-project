package com.agony.project.model.enums;

import lombok.Getter;

/**
 * @author Agony
 * @create 2024/4/7 21:00
 */
@Getter
public enum InterfaceInfoStatusEnum {

    ONLINE("上线", 1),
    OFFLINE("关闭", 0);

    private final String status;
    private final int value;

    InterfaceInfoStatusEnum(String status, int value) {
        this.status = status;
        this.value = value;
    }
    
}
