package com.littlekingkong.community.dto;

import lombok.Data;

import java.util.List;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/10 16:31*@since 1.0.0
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
