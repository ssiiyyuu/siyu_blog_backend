package com.siyu.service_oss.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import java.io.File;

/**
 * @author 创建人:< Chenmq>
 * @project 项目:<>
 * @date 创建时间:< 2017/8/30>
 * @comments: 说明:< //七牛云图片配置 >
 */
public class OssUtil {

    //设置好账号的ACCESS_KEY和SECRET_KEY
    final String ACCESS_KEY = "YOU_ACCESS_KEY";
    final String SECRET_KEY = "YOU_SECRET_KEY";
    //要上传的空间
    final String BUCKET_NAME = "创建的存储空间名称";

    /**
     * 七牛云上传图片
     * @param localFilePath
     * @return 外链
     */
    public String uoloapQiniu (File localFilePath,String fileName) {
        
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = ACCESS_KEY;
        String secretKey = SECRET_KEY;
        String bucket = BUCKET_NAME;

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        String result = null;

        try {
            //如果是Windows情况下，格式是 D:\23912475_130759767000_2.jpg
            //String localFilePath = "D:\\23912475_130759767000_2.jpg";
            //String localFilePath = "/home/qiniu/test.png";
            //默认不指定key的情况下，以文件内容的hash值作为文件名
            String key = "images/"+fileName+"?tId="+System.currentTimeMillis();
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            result = "外链域名(如:image.domain.com)"+putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
            }
            result = null;
        }
        return result;
    }

}