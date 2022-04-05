package com.littlekingkong.community.exception;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/3/29 21:29*@since 1.0.0
 */

public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;
    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }


    @Override
    public String getMessage() {
        return message;
    }


    public Integer getCode() {
        return code;
    }
}
