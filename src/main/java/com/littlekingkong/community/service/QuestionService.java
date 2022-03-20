package com.littlekingkong.community.service;

import com.littlekingkong.community.dto.PaginationDTO;
import com.littlekingkong.community.dto.QuestionDTO;
import com.littlekingkong.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.h2.mvstore.Page;
import org.springframework.stereotype.Service;

import java.util.List;


public interface QuestionService {
    Integer createQuestion(Question question);


    PaginationDTO list2(@Param("page") Integer page, @Param("size") Integer size);

    List<QuestionDTO> listQuestion(Integer page, Integer size);
}
