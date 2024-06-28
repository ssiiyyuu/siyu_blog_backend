package com.siyu.service_oss.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.siyu.common_static.enums.ExceptionEnum;
import com.siyu.service_base.exceptionHandler.GlobalException;
import com.siyu.service_oss.config.OssConfig;
import com.siyu.service_oss.service.OssService;

@Service
public class OssServiceImpl implements OssService{

    @Autowired
    private OssConfig ossConfig;

    @Override
    public String uploadFile(MultipartFile multipartFile, String prefix) {
        //华南
        Configuration cfg = new Configuration(Region.region2());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        UploadManager uploadManager = new UploadManager(cfg);

        InputStream inputStream;
        byte[] fileBytes;
        try {
            inputStream = multipartFile.getInputStream();
            fileBytes = inputStream.readAllBytes();
            inputStream.close();
        } catch (IOException e) {
            throw new GlobalException(ExceptionEnum.UPLOAD_FAILURE);
        }

        String accessKey = ossConfig.getAccessKey();
        String secretKey = ossConfig.getSecretKey();
        String bucket = ossConfig.getBucketName();
        String path = ossConfig.getUrl();
        String fileName = multipartFile.getOriginalFilename();
        String key = prefix + "/" + UUID.randomUUID().toString() + fileName;

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(fileBytes, key, upToken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return path + "/" + putRet.key;
        } catch (QiniuException e) {
            e.printStackTrace();
            throw new GlobalException(ExceptionEnum.UPLOAD_FAILURE);
        }
    }
    
}
