package com.maple.demo.config.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 笑小枫
 * @date 2022/7/22
 */
@Data
public class ImportContact {
    @ExcelProperty("公司名称")
    private String companyName;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("身份证号码")
    private String idCard;

    @ExcelProperty("电话号码")
    private String mobile;

    @ExcelProperty("职位")
    private String contactPostName;
}
