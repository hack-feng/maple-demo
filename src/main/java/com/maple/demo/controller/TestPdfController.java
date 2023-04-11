package com.maple.demo.controller;

import com.maple.demo.util.PdfUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 笑小枫
 * @date 2022/8/15
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
@Slf4j
@RestController
@RequestMapping("/example")
@Api(tags = "实例演示-PDF操作接口")
public class TestPdfController {

    @ApiOperation(value = "根据PDF模板导出PDF")
    @GetMapping("/exportPdf")
    public void exportPdf(HttpServletResponse response) {
        Map<String, Object> dataMap = new HashMap<>(16);
        dataMap.put("nickName", "笑小枫");
        dataMap.put("age", 18);
        dataMap.put("sex", "男");
        dataMap.put("csdnUrl", "https://zhangfz.blog.csdn.net/");
        dataMap.put("siteUrl", "https://www.xiaoxiaofeng.com/");
        dataMap.put("desc", "大家好，我是笑小枫。");

        Map<String, String> photoMap = new HashMap<>(16);
        photoMap.put("logo", "https://profile.csdnimg.cn/C/9/4/2_qq_34988304");

        // 设置response参数，可以打开下载页面
        response.reset();
        response.setCharacterEncoding("UTF-8");
        // 定义输出类型
        response.setContentType("application/PDF;charset=utf-8");
        // 设置名称
        response.setHeader("Content-Disposition", "attachment; filename=" + "xiaoxiaofeng.pdf");
        try (ServletOutputStream out = response.getOutputStream()) {
            // 模板路径记
            PdfUtil.fillTemplate(dataMap, photoMap, out, "src/main/resources/templates/xiaoxiaofeng.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "测试纯代码生成PDF并导出PDF")
    @GetMapping("/createAndExportPdf")
    public void createAndExportPdf(HttpServletResponse response) {
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setCharacterEncoding("UTF-8");
        // 定义输出类型
        response.setContentType("application/PDF;charset=utf-8");
        // 设置名称
        response.setHeader("Content-Disposition", "attachment; filename=" + "xiaoxiaofeng.pdf");
        try (ServletOutputStream out = response.getOutputStream()) {
            // 模板路径记
            PdfUtil.createAndExportPdfPage(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "测试纯代码生成PDF到指定目录")
    @GetMapping("/createPdfLocal")
    public void create() {
        PdfUtil.createPdfPage("D:\\test\\xxf.pdf");
    }

    @ApiOperation(value = "测试合并PDF到指定目录")
    @GetMapping("/mergePdf")
    public Boolean mergePdf() {
        String[] files = {"D:\\test\\1.pdf", "D:\\test\\2.pdf"};
        String newFile = "D:\\test\\xxf-merge.pdf";
        return PdfUtil.mergePdfFiles(files, newFile);
    }

    @ApiOperation(value = "测试合并PDF后并导出")
    @GetMapping("/exportMergePdf")
    public void createPdf(HttpServletResponse response) {
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setCharacterEncoding("UTF-8");
        // 定义输出类型
        response.setContentType("application/PDF;charset=utf-8");
        // 设置名称
        response.setHeader("Content-Disposition", "attachment; filename=" + "xiaoxiaofeng.pdf");
        try (ServletOutputStream out = response.getOutputStream()) {
            String[] files = {"D:\\test\\1.pdf", "D:\\test\\2.pdf"};
            // 生成为临时文件，转换为流后，再删除该文件
            String newFile = "src\\main\\resources\\templates\\" + UUID.randomUUID() + ".pdf";
            boolean isOk = PdfUtil.mergePdfFiles(files, newFile);
            if (isOk) {
                PdfUtil.readDeletePdf(newFile, out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
