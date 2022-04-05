package com.littlekingkong.community.advice;

import javax.jws.WebParam;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.littlekingkong.community.dto.ResultDTO;
import com.littlekingkong.community.exception.CustomizeErrorCode;
import com.littlekingkong.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.io.PrintWriter;

import static com.littlekingkong.community.dto.ResultDTO.errorOf;


/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/3/29 20:24*@since 1.0.0
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handle(HttpServletRequest request, Throwable ex,
                               Model model) {
        if(ex instanceof  NullPointerException){
            model.addAttribute("message","找不到问题了");
        }
        return new ModelAndView("error");
    }


    @ExceptionHandler(CustomizeException.class)
    public ModelAndView handle2(HttpServletRequest request,
                                HttpServletResponse response,
                                Throwable ex,
                               Model model) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO = new ResultDTO();
            if (ex instanceof CustomizeException) {
                resultDTO =  ResultDTO.errorOf((CustomizeException) ex);
            }else {
                resultDTO = (ResultDTO) ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
                //model.addAttribute("message", "服务器冒烟了！");
            }

            try {
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            if (ex instanceof CustomizeException ) {
                model.addAttribute("message", ex.getMessage());
            } else if(ex instanceof  NullPointerException){
                model.addAttribute("message",CustomizeErrorCode.PATH_NOT_FOUND.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYS_ERROR.getMessage());
            }
        }
        return new ModelAndView("error");
    }

}
