package com.littlekingkong.community.service.impl;

import com.littlekingkong.community.dao.QuestionMapper;
import com.littlekingkong.community.dao.UserMapper;
import com.littlekingkong.community.dto.PaginationDTO;
import com.littlekingkong.community.dto.QuestionDTO;
import com.littlekingkong.community.exception.CustomizeErrorCode;
import com.littlekingkong.community.exception.CustomizeException;
import com.littlekingkong.community.model.Question;
import com.littlekingkong.community.model.User;
import com.littlekingkong.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    @Resource
    private UserMapper userMapper;

    //创建问题
    @Override
    public Integer createQuestion(Question question) {
        return questionMapper.create(question);
    }


    //增加浏览数量


    @Override
    public void intQuestionView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setView_count(1);
        questionMapper.inView(question);
    }

    @Override
    public void createOrUpdate(Question question) {
        //如果id为空证明不存在，则新建
        if (question.getId() == null) {
                questionMapper.create(question);
        } else {
            // id 不为空，进行问题更新
            question.setGmt_modified(System.currentTimeMillis());
            int updated = questionMapper.update(question);
            System.out.println(updated);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    @Override
    public PaginationDTO list2(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer count = questionMapper.count();
        paginationDTO.setPagination(count, page, size);

        if (page < 1) {
            page = 1;
        }

        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }
        // 展示的页数，等于 （页数 - 1） * 每页数目
        Integer offset = (page - 1) * size;
        List<Question> questions = questionMapper.listQuestion2(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    @Override
    public List<QuestionDTO> listQuestion(Integer page, Integer size) {
        List<Question> questions = questionMapper.listQuestion(page, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        return questionDTOList;
    }

    //完成我的问题展示功能
    @Override
    public PaginationDTO listUserQuestion(Long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer count = questionMapper.countById(userId);
        //Integer count = questionMapper.count();
        paginationDTO.setPagination(count, page, size);

        if (page < 1) {
            page = 1;
        }

        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }
        // 展示的页数，等于 （页数 - 1） * 每页数目
        Integer offset = (page - 1) * size;
        if(offset < 0) {
            offset = 0;
        }
        List<Question> questions = questionMapper.listUserQuestion(userId,offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    //具体问题展示功能,根据id获取Question，再利用传输层QuestionDTO，把问题发起者信息封装进去
    @Override
    public QuestionDTO getById(Long id) {
        Question question = questionMapper.getById(id);

        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.findById(question.getCreator());
        System.out.println(user.getId() + " " + questionDTO.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public Question getQuestionById(Long id) {
        Question question = questionMapper.getById(id);
        return question;
    }

}
