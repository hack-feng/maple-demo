package com.maple.demo.config.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author 笑小枫
 * @date 2022/7/22
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
@Data
@Configuration
public class FileProperties {
    // ---------------本地文件配置 start------------------
    /**
     * 图片存储路径
     */
    @Value("${file.local.imageFilePath}")
    private String imageFilePath;
    
    /**
     * 文档存储路径
     */
    @Value("${file.local.docFilePath}")
    private String docFilePath;

    /**
     * 文件限制大小
     */
    @Value("${file.local.maxFileSize}")
    private long maxFileSize;
    // --------------本地文件配置 end-------------------


    // --------------华为云OBS配置 start----------------
    /**
     * AK
     */
    @Value("${file.obs.accessKey}")
    private String obsAccessKey;

    @Value("${file.obs.secretKey}")
    private String obsSecretKey;

    @Value("${file.obs.endPoint}")
    private String obsEndPoint;

    @Value("${file.obs.bucketName}")
    private String obsBucketName;
    // --------------华为云OBS配置 end-------------------
    
    // --------------又拍云OBS配置 start----------------
    /**
     * AK
     */
    @Value("${file.upy.bucketName}")
    private String upyBucketName;

    @Value("${file.upy.userName}")
    private String upyUserName;

    @Value("${file.upy.password}")
    private String upyPassword;

    @Value("${file.upy.showUrl}")
    private String upyShowUrl;
    // --------------又拍云OBS配置 end-------------------
    
}
