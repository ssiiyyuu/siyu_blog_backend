package com.siyu.service_oss.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "qiniu")
public class OssConfig {
    
    private String url;

    private String accessKey;
    
    private String secretKey;

    private String bucketName;

    
}
