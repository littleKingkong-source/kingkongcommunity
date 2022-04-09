package com.littlekingkong.community.service;

import com.littlekingkong.community.dto.PaginationDTO;
import com.littlekingkong.community.dto.QuestionDTO;
import com.littlekingkong.community.model.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface QuestionService {
    Integer createQuestion(Question question);

    // 主页分页展示
    PaginationDTO list2(@Param("page") Integer page, @Param("size") Integer size);

    List<QuestionDTO> listQuestion(Integer page, Integer size);


    // 展示我的问题页面
    PaginationDTO listUserQuestion(@Param("userId") Long userId, @Param("page") Integer page, @Param("size") Integer size);


    //根据id查询问题（包括User属性）
    QuestionDTO getById(@Param("id") Long id);

    // 根据id查询问题
    Question getQuestionById(@Param("id") Long id);

    // 更新问题
    void createOrUpdate(Question question);

    // 增加问题阅读数
    void intQuestionView(@Param("id") Long id);

    List<QuestionDTO> selectRelated(QuestionDTO questionDTO);
}
