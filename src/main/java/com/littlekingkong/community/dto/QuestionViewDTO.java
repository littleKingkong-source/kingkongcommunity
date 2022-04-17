package com.littlekingkong.community.dto;

import lombok.Data;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/17 22:32*@since 1.0.0
 */
@Data
public class QuestionViewDTO {
    private Long id;
    private Integer view_count;
    private Integer like_count;

}
