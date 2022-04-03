package com.littlekingkong.community.service;

import com.littlekingkong.community.dto.PaginationDTO;
import com.littlekingkong.community.dto.QuestionDTO;
import com.littlekingkong.community.model.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface QuestionService {
    Integer createQuestion(Question question);


    PaginationDTO list2(@Param("page") Integer page, @Param("size") Integer size);

    List<QuestionDTO> listQuestion(Integer page, Integer size);

    PaginationDTO listUserQuestion(@Param("userId") Integer userId, @Param("page") Integer page, @Param("size") Integer size);

    QuestionDTO getById(@Param("id") Integer id);

    Question getQuestionById(@Param("id") Integer id);

    void createOrUpdate(Question question);



    void intQuestionView(@Param("id") Integer id);
}
