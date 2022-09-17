package com.maple.demo.util.file;

import com.maple.demo.config.bean.ErrorCode;
import com.maple.demo.config.bean.FileProperties;
import com.maple.demo.config.exception.MapleCheckException;
import com.maple.demo.util.common.DateUtil;
import com.upyun.RestManager;
import com.upyun.UpException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;

/**
 * 又拍云 对象存储工具类
 * 又拍云客户端配置：https://help.upyun.com/knowledge-base/quick_start/
 * 又拍云官方sdk：https://github.com/upyun/java-sdk
 *
 * @author 笑小枫
 * @date 2022/7/22
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
@Slf4j
@Component
@AllArgsConstructor
public class UpyOssUtil {

    private final FileProperties fileProperties;

    /**
     * 根据url上传文件到七牛云
     */
    public String uploadUpy(String url) {
        String filePath = setFilePath();
        String dateStr = DateUtil.dateToStr(new Date(), DateUtil.YYMMDDHHMMSS);
        String fileName = filePath + "xxf-" + dateStr + url.substring(url.lastIndexOf("."));
        RestManager restManager = new RestManager(fileProperties.getObsBucketName(), fileProperties.getUpyUserName(), fileProperties.getUpyPassword());

        URI u = URI.create(url);
        try (InputStream inputStream = u.toURL().openStream()) {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            Response response = restManager.writeFile(fileName, bytes, null);
            if (response.isSuccessful()) {
                return fileProperties.getUpyShowUrl() + fileName;
            }
        } catch (IOException | UpException e) {
            e.printStackTrace();
        }
        throw new MapleCheckException(ErrorCode.COMMON_ERROR, "又拍云oss上传文件失败，请重试");
    }

    /**
     * MultipartFile上传文件到七牛云
     */
    public String uploadUpy(MultipartFile file) {
        String dateStr = DateUtil.dateToStr(new Date(), DateUtil.YYMMDDHHMMSS);
        String fileName = file.getOriginalFilename();
        if (StringUtils.isBlank(fileName)) {
            throw new MapleCheckException(ErrorCode.COMMON_ERROR, "获取文件信息失败");
        }
        String objectKey = setFilePath() + dateStr + fileName.substring(file.getOriginalFilename().lastIndexOf("."));
        RestManager restManager = new RestManager(fileProperties.getObsBucketName(), fileProperties.getUpyUserName(), fileProperties.getUpyPassword());

        try (InputStream inputStream = file.getInputStream()) {
            Response response = restManager.writeFile(objectKey, inputStream, null);
            if (response.isSuccessful()) {
                return fileProperties.getUpyShowUrl() + fileName;
            }
        } catch (IOException | UpException e) {
            e.printStackTrace();
        }
        throw new MapleCheckException(ErrorCode.COMMON_ERROR, "又拍云oss上传文件失败，请重试");
    }

    /**
     * 设置文件上传路径
     */
    private String setFilePath() {
        Calendar calendar = Calendar.getInstance();
        String flag = "/";
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        return "/spider/" + year + flag + month + flag + day + flag;
    }

}