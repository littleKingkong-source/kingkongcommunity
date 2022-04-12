package com.littlekingkong.community.model;

import lombok.Data;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/10 22:23*@since 1.0.0
 */
@Data
public class Notification {
    private Long id;
    private Long notifier;
    private Long receiver;
    private Long outerId;
    private Integer type;
    private Long gmt_create;
    private Integer status;
    private String notifier_name;
    private String  outer_title;
}
