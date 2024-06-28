package com.siyu.service_oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {

    public String uploadFile(MultipartFile multipartFile, String prefix);
}
