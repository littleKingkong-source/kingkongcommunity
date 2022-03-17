package com.littlekingkong.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/3/16 20:50*@since 1.0.0
 */
@Controller
public class PublishController {
    @GetMapping("/publish")
    public String publish() {
        return "/editor/publish";
    }
}
