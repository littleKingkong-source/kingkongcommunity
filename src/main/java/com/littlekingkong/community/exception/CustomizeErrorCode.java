package com.littlekingkong.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND("你找得问题不在了，要不换个试试？"),
    SERVICE_NOT_FOUND("服务丢失了，稍后再来吧"),
    PATH_NOT_FOUND("路径不存在！");

    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}