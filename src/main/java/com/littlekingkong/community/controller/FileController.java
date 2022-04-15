package com.littlekingkong.community.controller;

import com.littlekingkong.community.dto.FileDTO;
import com.littlekingkong.community.provider.OSSClientProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/14 21:12*@since 1.0.0
 */
@Controller
public class FileController {
    @Autowired
    private OSSClientProvider ossClientProvider;


    @PostMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        System.out.println(file);
        String fileUrl = ossClientProvider.uploadImg2Oss(file);
        System.out.println(fileUrl);
        String ImgUrl = ossClientProvider.getImgUrl(fileUrl);
        System.out.println(ImgUrl);
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl(ImgUrl);

        return fileDTO;
    }
}
