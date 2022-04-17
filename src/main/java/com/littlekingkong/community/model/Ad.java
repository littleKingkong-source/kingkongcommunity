package com.littlekingkong.community.model;

import lombok.Data;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/16 16:57*@since 1.0.0
 */
@Data
public class Ad {
    private Integer id;
    private String title;
    private String url;
    private String image;
    private Long gmt_start;
    private Long gmt_end;
    private Long gmt_modified;
    private Integer status;
    private String pos;
}
