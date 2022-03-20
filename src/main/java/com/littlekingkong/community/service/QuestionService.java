package com.littlekingkong.community.service;

import com.littlekingkong.community.dto.PaginationDTO;
import com.littlekingkong.community.dto.QuestionDTO;
import com.littlekingkong.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;


public interface QuestionService {
    Integer createQuestion(Question question);

    PaginationDTO list(@Param("page") Integer page, Integer size);


    List<QuestionDTO> listQuestion();
}
