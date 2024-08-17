package com.agony.project.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 删除请求
 *
 * @auther Agony
 * @create 2024/1/2 10:02
 * @Version 1.0
 */
@Data
public class DeleteRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    @Serial
    private static final long serialVersionUID = 1L;
}