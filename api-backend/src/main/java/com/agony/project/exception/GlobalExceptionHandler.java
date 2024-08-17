package com.agony.project.exception;

import com.agony.project.common.ResultData;
import com.agony.project.common.ReturnCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @auther Agony
 * @create 2024/1/2 10:16
 * @Version 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResultData<?> businessExceptionHandler(BusinessException e) {
        log.error("businessException: " + e.getMessage(), e);
        return ResultData.fail(ReturnCodeEnum.RC500.getCode(), e.getMessage());
    }

    // @ExceptionHandler(RuntimeException.class)
    // public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
    //     log.error("runtimeException", e);
    //     return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage());
    // }


    @ExceptionHandler(RuntimeException.class)
    public ResultData<?> exceptionHandler(BusinessException e) {
        log.error("businessException: " + e.getMessage(), e);
        return ResultData.fail(ReturnCodeEnum.RC500.getCode(), e.getMessage());
    }
}
