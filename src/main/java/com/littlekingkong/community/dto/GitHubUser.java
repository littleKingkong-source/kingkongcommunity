package com.littlekingkong.community.dto;

import org.springframework.stereotype.Component;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/3/11 23:58*@since 1.0.0
 */
@Component
public class GitHubUser {
    private String name;
    private Long id;
    private String bio;

    @Override
    public String toString() {
        return "GitHubUser{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", bio='" + bio + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
