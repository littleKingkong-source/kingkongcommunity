package com.littlekingkong.community.service.impl;

import com.littlekingkong.community.dao.CommentMapper;
import com.littlekingkong.community.dao.QuestionMapper;
import com.littlekingkong.community.enums.CommentTypeEnum;
import com.littlekingkong.community.exception.CustomizeErrorCode;
import com.littlekingkong.community.exception.CustomizeException;
import com.littlekingkong.community.model.Comment;
import com.littlekingkong.community.model.Question;
import com.littlekingkong.community.service.CommentService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.comments.CommentType;

import javax.annotation.Resource;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/3 12:33*@since 1.0.0
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private  QuestionMapper questionMapper;

    @Transactional
    @Override
    public void insertComment(Comment comment) {
        if (comment.getParent_id() == null || comment.getParent_id() == 0) {
            throw  new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbComment = commentMapper.selectById(comment.getParent_id());
            if (dbComment == null) {
                throw  new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
            }
            commentMapper.insertComment(comment);
        } else {
            // 回复问题
            Question question =  questionMapper.getById(comment.getParent_id());
            if (question == null) {
                throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insertComment(comment);
            question.setComment_count(1);
            questionMapper.intCommentCount(question);
        }

    }
}
