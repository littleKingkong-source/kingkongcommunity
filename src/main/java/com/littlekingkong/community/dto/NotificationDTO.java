package com.littlekingkong.community.dto;

import com.littlekingkong.community.model.User;
import lombok.Data;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/11 21:00*@since 1.0.0
 */
@Data
public class NotificationDTO {
    private Long id;
    private  Integer status;
    private  Long gmt_create;
    private Long notifier;
    private String notifier_name;
    private String outer_title;
    private String typeName;
    private Integer type;
    private Long outerId;
}
