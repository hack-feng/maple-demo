package com.maple.demo.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSON;
import com.maple.demo.config.bean.excel.ExcelCompany;
import com.maple.demo.config.bean.excel.ExcelContact;
import com.maple.demo.listener.ImportExcelListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 笑小枫
 * @date 2022/7/22
 */
@Slf4j
@RestController
@RequestMapping("/example")
public class TestImportExcelController {

    @PostMapping("/importExcel")
    public Map<String, List<String>> importExcel(@RequestParam(value = "file") MultipartFile file) {
        List<String> companyErrorList = new ArrayList<>();
        List<String> contactErrorList = new ArrayList<>();
        try (ExcelReader excelReader = EasyExcelFactory.read(file.getInputStream()).build()) {
            // 公司信息构造器
            ReadSheet dealerSheet = EasyExcelFactory
                    .readSheet(0)
                    .head(ExcelCompany.class)
                    .registerReadListener(new ImportExcelListener<ExcelCompany>(data -> {
                        // 处理你的业务逻辑，最好抽出一个方法单独处理逻辑
                        log.info("公司信息数据----------------------------------------------");
                        log.info("公司信息数据：" + JSON.toJSONString(data));
                        log.info("公司信息数据----------------------------------------------");
                    }, companyErrorList))
                    .headRowNumber(2)
                    .build();

            // 联系人信息构造器
            ReadSheet contactSheet = EasyExcelFactory
                    .readSheet(1)
                    .head(ExcelContact.class)
                    .registerReadListener(new ImportExcelListener<ExcelContact>(data -> {
                        // 处理你的业务逻辑，最好抽出一个方法单独处理逻辑
                        log.info("联系人信息数据------------------------------------------");
                        log.info("联系人信息数据：" + JSON.toJSONString(data));
                        log.info("联系人信息数据------------------------------------------");
                    }, contactErrorList))
                    .build();

            // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            excelReader.read(dealerSheet, contactSheet);
        } catch (IOException e) {
            log.error("处理excel失败，" + e.getMessage());
        }
        Map<String, List<String>> result = new HashMap<>(16);
        result.put("company", companyErrorList);
        result.put("contact", contactErrorList);
        log.info("导入excel完成，返回结果如下：" + JSON.toJSONString(result));
        return result;
    }
}
