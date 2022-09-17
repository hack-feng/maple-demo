package com.maple.demo.util.file;

import com.alibaba.fastjson.JSON;
import com.maple.demo.config.bean.ErrorCode;
import com.maple.demo.config.bean.FileProperties;
import com.maple.demo.config.exception.MapleCheckException;
import com.maple.demo.util.common.DateUtil;
import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.PutObjectResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author 笑小枫
 * @date 2022/7/22
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
@Slf4j
@Component
@AllArgsConstructor
public class HwyObsUtil {

    private final FileProperties fileProperties;

    /**
     * 上传华为云obs文件存储
     *
     * @param file 文件
     * @return 文件访问路径
     */
    public String uploadFileToObs(MultipartFile file) {
        String dateStr = DateUtil.dateToStr(new Date(), DateUtil.YYMMDDHHMMSS);
        String fileName = file.getOriginalFilename();
        if (StringUtils.isBlank(fileName)) {
            throw new MapleCheckException(ErrorCode.COMMON_ERROR, "获取文件信息失败");
        }
        String fileType = fileName.substring(file.getOriginalFilename().lastIndexOf("."));
        String objectKey = dateStr + fileType;
        PutObjectResult putObjectResult;
        try (InputStream inputStream = file.getInputStream();
             ObsClient obsClient = new ObsClient(fileProperties.getObsAccessKey(), fileProperties.getObsSecretKey(), fileProperties.getObsEndPoint())) {
            log.info(String.format("华为云obs上传开始，原文件名：%s，上传后的文件名：%s", fileName, objectKey));
            putObjectResult = obsClient.putObject(fileProperties.getObsBucketName(), objectKey, inputStream);
            log.info(String.format("华为云obs上传结束，文件名：%s，返回结果：%s", objectKey, JSON.toJSONString(putObjectResult)));
        } catch (ObsException | IOException e) {
            log.error("华为云obs上传文件失败", e);
            throw new MapleCheckException(ErrorCode.COMMON_ERROR, "华为云obs上传文件失败，请重试");
        }
        if (putObjectResult.getStatusCode() == 200) {
            return putObjectResult.getObjectUrl();
        } else {
            throw new MapleCheckException(ErrorCode.COMMON_ERROR, "华为云obs上传文件失败，请重试");
        }
    }
}
