package com.littlekingkong.community.service.impl;

import com.littlekingkong.community.dao.CommentMapper;
import com.littlekingkong.community.dao.NotificationMapper;
import com.littlekingkong.community.dao.QuestionMapper;
import com.littlekingkong.community.dao.UserMapper;
import com.littlekingkong.community.dto.CommentQuestionDTO;
import com.littlekingkong.community.enums.CommentTypeEnum;
import com.littlekingkong.community.enums.NotificationStatusEnum;
import com.littlekingkong.community.enums.NotificationTypeEnum;
import com.littlekingkong.community.exception.CustomizeErrorCode;
import com.littlekingkong.community.exception.CustomizeException;
import com.littlekingkong.community.model.Comment;
import com.littlekingkong.community.model.Notification;
import com.littlekingkong.community.model.Question;
import com.littlekingkong.community.model.User;
import com.littlekingkong.community.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Resource
    private NotificationMapper notificationMapper;

    @Resource
    private UserMapper userMapper;
    @Override
    public List<CommentQuestionDTO>  listByTargetId(Long id, CommentTypeEnum type) {
        // 获取评论内容
        Comment Questioncomment = new Comment();
        Questioncomment.setType(type.getType());
        Questioncomment.setParent_id(id);
        List<Comment> comments = commentMapper.selectByQuestion(Questioncomment);
        // 获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment->comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);
        List<User> users = userMapper.selectByList(userIds);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user->user.getId(),user->user));

        // 转comment 为 commentQuestionDTO
        List<CommentQuestionDTO> commentDTOS = comments.stream().map(comment-> {
            CommentQuestionDTO commentQuestionDTO = new CommentQuestionDTO();
            BeanUtils.copyProperties(comment, commentQuestionDTO);
            commentQuestionDTO.setUser(userMap.get(comment.getCommentator()));
            return commentQuestionDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }

    @Transactional
    @Override
    public void insertComment(Comment comment,User commenter) {
        if (comment.getParent_id() == null || comment.getParent_id() == 0) {
            throw  new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
//        System.out.println("可以执行到这里");
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //被回复的评论 dbComment
            Comment dbComment = commentMapper.selectSubCommentById(comment.getParent_id());

            if (dbComment == null) {
                throw  new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            // 回复评论持久化
            commentMapper.insertComment(comment);

            // 获取问题
            Question question =  questionMapper.getById(dbComment.getParent_id());
            if (question == null) {
                throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }




            // 增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParent_id());
            parentComment.setComment_count(1);
            commentMapper.updateCommentCount(parentComment);

            // 创建通知
            createNotifiy(comment, dbComment.getCommentator(), commenter.getName(), question.getTitle(),NotificationTypeEnum.REPLY_COMMENT,question.getId());

        } else {
            // 回复问题
            Question question =  questionMapper.getById(comment.getParent_id());
            if (question == null) {
                throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insertComment(comment);
            question.setComment_count(1);
            questionMapper.intCommentCount(question);

            // 创建通知
           createNotifiy(comment, question.getCreator(), commenter.getName(), question.getTitle(),NotificationTypeEnum.REPLY_QUESTION,question.getId());
        }

    }

    private void createNotifiy(Comment comment, Long receiver, String notifierName, String outerTitle,NotificationTypeEnum typeEnum, Long outerId) {
        // 通知回复
        Notification notification = new Notification();
        notification.setGmt_create(System.currentTimeMillis());
        notification.setType(typeEnum.getType());
        // 回复的问题的id
        notification.setOuterId(outerId);
        // 回复发起者
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        // 被回复的那个人
        notification.setReceiver(receiver);
        notification.setNotifier_name(notifierName);
        notification.setOuter_title(outerTitle);
        notificationMapper.insert(notification);
    }
}
