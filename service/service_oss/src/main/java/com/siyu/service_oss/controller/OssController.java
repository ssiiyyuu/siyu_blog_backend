package com.siyu.service_oss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.siyu.service_base.result.Result;
import com.siyu.service_oss.service.OssService;

@RestController
@RequestMapping("/service_oss")
public class OssController {

    //TODO delete
    
    @Autowired
    OssService ossService;

    @PostMapping("/avatar")
    public Result uploadAvatar(@RequestPart("file") MultipartFile file) {
        String key = ossService.uploadFile(file, "avatar");
        return Result.ok().data("key", key);
    }
    
    @PostMapping("/cover")
    public Result uploadCover(@RequestPart("file") MultipartFile file) {
        String key = ossService.uploadFile(file, "cover");
        return Result.ok().data("key", key);
    }

    @PostMapping("/img")
    public Result uploadImg(@RequestPart("file") MultipartFile file) {
        String key = ossService.uploadFile(file, "img");
        return Result.ok().data("key", key);
    }
}
