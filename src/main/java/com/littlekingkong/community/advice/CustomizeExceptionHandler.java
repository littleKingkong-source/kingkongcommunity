package com.littlekingkong.community.advice;

import javax.jws.WebParam;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import com.littlekingkong.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


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
    public ModelAndView handle2(HttpServletRequest request, Throwable ex,
                               Model model) {
        if (ex instanceof CustomizeException ) {
            model.addAttribute("message", ex.getMessage());
        } else if(ex instanceof  NullPointerException){
            model.addAttribute("message","找不到问题了");
        } else {
            model.addAttribute("message", "服务冒烟了，要不然你稍后再试试把！！");
        }
        return new ModelAndView("error");
    }

}
