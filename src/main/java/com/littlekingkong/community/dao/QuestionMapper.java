package com.littlekingkong.community.dao;
import com.littlekingkong.community.dto.QuestionDTO;
import com.littlekingkong.community.model.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface QuestionMapper {
    Integer create(Question question);

    List<Question> list(@Param("offset") Integer offset, @Param("size") Integer size);

    Integer count();

    List<Question> listQuestion();;

}
