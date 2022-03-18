package com.littlekingkong.community.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/3/11 22:49*@since 1.0.0
 */
@Component
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;


}
