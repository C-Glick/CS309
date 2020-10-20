package com.tc_4.carbon_counter.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;


@RestController
public class NewsController {
    
    @GetMapping("/image/{fileName}")
    public void getImage(HttpServletResponse response, @PathVariable String fileName) throws IOException {
        
        Resource imgFile;
        try{
            //file system file (should be server file system, temporarily my machine)
            imgFile = new FileSystemResource("C:/Users/The_Pheonix/git/309_Project/Backend/carbon_counter/src/main/resources/images/" + fileName);
        } catch(Exception e){
            //class path file
            imgFile = new ClassPathResource("/images/" + fileName);
        }

        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
    }
}
