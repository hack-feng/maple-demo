package com.maple.demo.controller;

import com.maple.demo.config.bean.ErrorCode;
import com.maple.demo.config.exception.MapleCheckException;
import com.maple.demo.util.file.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author 笑小枫
 * @date 2022/7/22
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "文件相关操作接口")
@RequestMapping("/example")
public class TestFileController {

    private final FileUtil fileUtil;

    @PostMapping("/uploadImage")
    @ApiOperation("图片上传")
    public String uploadImage(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new MapleCheckException(ErrorCode.COMMON_ERROR, "图片内容为空，上传失败!");
        }
        return fileUtil.uploadImage(file);
    }

    @PostMapping("/uploadFiles")
    @ApiOperation("文件批量上传")
    public List<Map<String, Object>> uploadFiles(@RequestParam(value = "file") MultipartFile[] files) {
        return fileUtil.uploadFiles(files);
    }


    @PostMapping("/deleteFiles")
    @ApiOperation("批量删除文件")
    public void deleteFiles(@RequestParam(value = "files") String[] files) {
        fileUtil.deleteFile(files);
    }

    @GetMapping(value = "/download/{fileName:.*}")
    @ApiOperation("文件下载功能")
    public void download(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        try {
            fileUtil.downLoadFile(response, fileName);
        } catch (Exception e) {
            log.error("文件下载失败", e);
        }
    }
}
