package com.littlekingkong.community.enums;

import com.littlekingkong.community.model.Comment;
import org.yaml.snakeyaml.comments.CommentType;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/3 14:36*@since 1.0.0
 */

public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType() == type) {
                return true;
            }
        }
        return false;
    }
}
