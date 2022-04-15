package com.littlekingkong.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001,"你找得问题不在了，要不换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复！"),
    SERVICE_NOT_FOUND(2003,"服务丢失了，稍后再来吧"),
    PATH_NOT_FOUND(2004,"路径不存在！"),
    NOT_LOGIN(2005,"当前操作需要登录，请登录后重试！"),
    SYS_ERROR(2006,"服务器冒烟了，请稍后再试！！"),
    TYPE_PARAM_WRONG(2007,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2008,"回复的评论不存在"),
    COMMENT_IS_NULL(2009,"输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2010,"朋友你可能错误读取到别人的信息了！！！"),
    NOTIFICATION_NOT_FOUND(2011,"消息飞走了，你错过了！！！"),
    FILE_UPLOAD_FAIL(2012,"上传失败"),
    IMAGE_OT_FOUND(2013,"没有找到图片地址！！");

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;

    }

    CustomizeErrorCode(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

}