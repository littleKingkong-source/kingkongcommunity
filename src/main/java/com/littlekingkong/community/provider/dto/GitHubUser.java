package com.littlekingkong.community.provider.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/3/11 23:58*@since 1.0.0
 */
@Component
@Data
public class GitHubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;

}
