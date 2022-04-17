package com.littlekingkong.community.provider.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/3/25 15:19*@since 1.0.0
 */
@Data
@Component
public class GiteeUser {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;
}
