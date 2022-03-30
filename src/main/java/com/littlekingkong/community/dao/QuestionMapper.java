package com.littlekingkong.community.dao;
import com.littlekingkong.community.dto.QuestionDTO;
import com.littlekingkong.community.model.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface QuestionMapper {
    Integer create(Question question);


    Integer count();

    List<Question> listQuestion(@Param("page") Integer page, @Param("size") Integer size);


    List<Question> listQuestion2(@Param("offset") Integer offset, @Param("size") Integer size);;

    List<Question> listUserQuestion(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("size") Integer size);

    Integer countById(@Param("userId") Integer userId);

    Question getById(@Param("id") Integer id);

    Integer update(Question question);
}
