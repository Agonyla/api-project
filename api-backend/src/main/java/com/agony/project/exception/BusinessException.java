package com.agony.project.exception;


import com.agony.project.common.ReturnCodeEnum;
import lombok.Getter;

/**
 * 自定义异常类
 *
 * @auther Agony
 * @create 2024/1/2 10:16
 * @Version 1.0
 */
@Getter
public class BusinessException extends RuntimeException {

    private final String code;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ReturnCodeEnum returnCodeEnum) {
        super(returnCodeEnum.getMessage());
        this.code = returnCodeEnum.getCode();
    }

    public BusinessException(ReturnCodeEnum returnCodeEnum, String message) {
        super(message);
        this.code = returnCodeEnum.getCode();
    }

}
