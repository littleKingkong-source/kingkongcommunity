package com.littlekingkong.community.dto;

import lombok.Data;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/16 0:11*@since 1.0.0
 */
@Data
public class HotTagDTO implements Comparable {
    private String name;
    private Integer priority;

    public int compareTo(Object o) {
        return this.getPriority() - ((HotTagDTO) o ).getPriority();
    }
}
