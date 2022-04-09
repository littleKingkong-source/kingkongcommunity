package com.littlekingkong.community.controller;

import com.littlekingkong.community.dto.CommentQuestionDTO;
import com.littlekingkong.community.dto.QuestionDTO;
import com.littlekingkong.community.enums.CommentTypeEnum;
import com.littlekingkong.community.service.CommentService;
import com.littlekingkong.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/3/22 16:52*@since 1.0.0
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    //进入问题详情页面
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model,HttpServletRequest request) {
        // 增加阅读数
        questionService.intQuestionView(id);

        // 查询问题展示
        QuestionDTO questionDTO = questionService.getById(id);

        List<QuestionDTO> relatedQuestion = questionService.selectRelated(questionDTO);
        relatedQuestion.forEach(c-> System.out.println(c));
        System.out.println("--------");
        List<CommentQuestionDTO> commentDTOList = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

        model.addAttribute("question", questionDTO);
        model.addAttribute("commentDTOList",commentDTOList);
        model.addAttribute("relatedQuestion",relatedQuestion);
        return "question";
    }
}
