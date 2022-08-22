package com.maple.demo.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.maple.demo.config.bean.excel.ExportCompany;
import com.maple.demo.config.bean.excel.ExportContact;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 笑小枫
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 * @date 2022/7/22
 */
@Slf4j
@RestController
@RequestMapping("/example")
@Api(tags = "实例演示-导出Excel")
public class TestExportExcelController {


    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) {
        try (OutputStream out = response.getOutputStream()) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("笑小枫测试导出", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            handleExcel(out);
            out.flush();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void handleExcel(OutputStream out) {
        try (ExcelWriter excelWriter = EasyExcelFactory.write(out).build()) {
            WriteSheet dealerSheet = EasyExcelFactory.writerSheet(0, "经销商信息").head(ExportCompany.class).build();
            WriteSheet contactSheet = EasyExcelFactory.writerSheet(1, "联系人").head(ExportContact.class).build();
            excelWriter.write(getCompany(), dealerSheet);
            excelWriter.write(getContact(), contactSheet);
        }
    }

    private List<ExportCompany> getCompany() {
        List<ExportCompany> companyList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            companyList.add(ExportCompany.builder()
                    .companyName("笑小枫公司" + i)
                    .province("上海市")
                    .businessProvinceName("山东省")
                    .businessCityName("临沂市")
                    .businessAreaName("河东区")
                    .entStatus("营业")
                    .netAddress("www.xiaoxiaofeng.com")
                    .csdnAddress("https://zhangfz.blog.csdn.net")
                    .employeeMaxCount("100")
                    .startDate(new Date())
                    .build());
        }
        return companyList;
    }

    private List<ExportContact> getContact() {
        List<ExportContact> contactList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            contactList.add(ExportContact.builder()
                    .companyName("笑小枫公司" + i)
                    .name("笑小枫" + i)
                    .mobile("183000000000")
                    .idCard("371324199011111111")
                    .contactPostName("后端")
                    .build());
        }
        return contactList;
    }
}
