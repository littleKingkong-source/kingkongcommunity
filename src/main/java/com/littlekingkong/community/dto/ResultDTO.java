package com.littlekingkong.community.dto;

import com.littlekingkong.community.exception.CustomizeErrorCode;
import com.littlekingkong.community.exception.CustomizeException;
import lombok.Data;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/3 13:25*@since 1.0.0
 */
@Data
public class ResultDTO {
    private Integer code;
    private String message;

    public static ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(), e.getMessage());
    }

    public static Object errorOf(CustomizeErrorCode notLogin) {
        return errorOf(notLogin.getCode(), notLogin.getMessage());
    }

    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }
}
