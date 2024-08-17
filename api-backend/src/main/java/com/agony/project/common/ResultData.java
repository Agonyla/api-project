package com.agony.project.common;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @auther Agony
 * @create 2024/3/31 17:59
 */
@Data
@Accessors(chain = true)
public class ResultData<T> {
    private String code;
    private String message;
    private T data;
    private long timestamp;

    public ResultData() {
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ResultData<T> success(T data) {
        return new ResultData<T>().setCode(ReturnCodeEnum.RC200.getCode())
                .setMessage(ReturnCodeEnum.RC200.getMessage())
                .setData(data);
    }

    public static <T> ResultData<T> fail(String code, String message) {
        return new ResultData<T>().setCode(code)
                .setMessage(message)
                .setData(null);
    }
}
