package com.littlekingkong.community.service.impl;

import com.littlekingkong.community.dao.QuestionMapper;
import com.littlekingkong.community.dao.UserMapper;
import com.littlekingkong.community.dto.PaginationDTO;
import com.littlekingkong.community.dto.QuestionDTO;
import com.littlekingkong.community.dto.QuestionQueryDTO;
import com.littlekingkong.community.enums.SortEnum;
import com.littlekingkong.community.exception.CustomizeErrorCode;
import com.littlekingkong.community.exception.CustomizeException;
import com.littlekingkong.community.model.Question;
import com.littlekingkong.community.model.User;
import com.littlekingkong.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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


    // 正则筛选标签tag
    @Override
    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        if (StringUtils.isBlank(questionDTO.getTag())) {
            return new ArrayList<>();
        }
//        Question question = new Question();
//        question.setId(questionDTO.getId());
//        question.setTag(StringUtils.replace(questionDTO.getTag(),",","|"));
        String[] tags = StringUtils.split(questionDTO.getTag(), ",");
        String regexTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(regexTag);

        List<Question> questionList = questionMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questionList.stream().map(q -> {
            QuestionDTO questionDTO1 = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO1);
            return questionDTO1;
        }).collect(Collectors.toList());
        return questionDTOS;
    }

    // 实现观看人数递增
    @Override
    public void intQuestionView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setView_count(1);
        questionMapper.inView(question);
    }


    // 实现问题编辑更新
    @Override
    public void createOrUpdate(Question question) {
        //如果id为空证明不存在，则新建
        if (question.getId() == null) {
                questionMapper.create(question);
        } else {
            // id 不为空，进行问题更新
            question.setGmt_modified(System.currentTimeMillis());
            int updated = questionMapper.update(question);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }


    // 首页问题分页展示
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
        paginationDTO.setData(questionDTOList);

        return paginationDTO;
    }


    // 搜索分页展示
    @Override
    public PaginationDTO listSearch(String search, String tag,Integer page, Integer size) {

        if (StringUtils.isNoneBlank(search)) {
            String[] tags = StringUtils.split(search, " ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }


        PaginationDTO paginationDTO = new PaginationDTO();
        //Integer count = questionMapper.count();
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        questionQueryDTO.setTag(tag);
        Integer count = questionMapper.countBySearch(questionQueryDTO);


        paginationDTO.setPagination(count, page, size);

        if (page < 1) {
            page = 1;
        }

        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }
        // 展示的页数，等于 （页数 - 1） * 每页数目
        Integer offset = (page - 1) * size;

        questionQueryDTO.setSize(size);
        questionQueryDTO.setPage(offset);

        //List<Question> questions = questionMapper.listQuestion2(offset, size);
        List<Question> questions = questionMapper.selectBySearch(questionQueryDTO);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);

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

        // 查询指定条数的问题
        List<Question> questions = questionMapper.listUserQuestion(userId,offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);

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
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public Question getQuestionById(Long id) {
        Question question = questionMapper.getById(id);
        return question;
    }

    @Override
    public PaginationDTO listZeroCommentQuestion(Integer page, Integer size) {

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



        List<Question> questions = questionMapper.listZeroCommentQuestion2(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);

        return paginationDTO;
    }

    @Override
    public PaginationDTO listNewQuestion(String search, String tag, String sort, Integer page, Integer size) {

        if (StringUtils.isNotBlank(search)) {
            String[] tags = StringUtils.split(search, " ");
            search = Arrays
                    .stream(tags)
                    .filter(StringUtils::isNotBlank)
                    .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.joining("|"));
        }

        PaginationDTO paginationDTO = new PaginationDTO();

        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        if (StringUtils.isNotBlank(tag)) {
            tag = tag.replace("+", "").replace("*", "").replace("?", "");
            questionQueryDTO.setTag(tag);
        }

        for (SortEnum sortEnum : SortEnum.values()) {
            if (sortEnum.name().toLowerCase().equals(sort)) {
                questionQueryDTO.setSort(sort);
                System.out.println(sort);
                if (sortEnum == SortEnum.HOT7) {
                    questionQueryDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 7);
                }
                if (sortEnum == SortEnum.HOT30) {
                    questionQueryDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30);
                }
                break;
            }
        }
        System.out.println("---------------------" + questionQueryDTO + "------------------------");
        Integer count = questionMapper.countByNewSearch(questionQueryDTO);

        paginationDTO.setPagination(count, page, size);

        if (page < 1) {
            page = 1;
        }

        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }
        // 展示的页数，等于 （页数 - 1） * 每页数目
        Integer offset = (page - 1) * size;

        questionQueryDTO.setSize(size);
        questionQueryDTO.setPage(offset);

        List<Question> questions = questionMapper.selectByTypeSearch(questionQueryDTO);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);

        return paginationDTO;
    }
}
