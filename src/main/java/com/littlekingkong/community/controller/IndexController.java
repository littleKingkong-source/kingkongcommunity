package com.littlekingkong.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/3/11 16:24*@since 1.0.0
 */
@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

}
