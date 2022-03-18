package com.littlekingkong.community.service;

import com.littlekingkong.community.model.Question;
import org.springframework.stereotype.Service;


public interface QuestionService {
    Integer createQuestion(Question question);

}
