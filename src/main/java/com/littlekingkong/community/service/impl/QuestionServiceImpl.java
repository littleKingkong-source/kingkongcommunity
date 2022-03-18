package com.littlekingkong.community.service.impl;

import com.littlekingkong.community.dao.QuestionMapper;
import com.littlekingkong.community.model.Question;
import com.littlekingkong.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/3/17 19:38*@since 1.0.0
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private QuestionMapper questionMapper;

    //创建问题
    @Override
    public Integer createQuestion(Question question) {

        return questionMapper.create(question);
    }
}
