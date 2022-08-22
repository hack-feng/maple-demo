package com.maple.demo.config.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import java.util.Date;

/**
 * @author 笑小枫
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 * @date 2022/7/22
 */
@Data
public class ImportCompany {

    // -------------------- 基本信息 start -------------

    @ExcelProperty("公司名称")
    private String companyName;

    @ExcelProperty("省份")
    private String province;

    @ExcelProperty("成立时间")
    private Date startDate;

    @ExcelProperty("企业状态")
    private String entStatus;

    @ExcelProperty("注册地址")
    private String registerAddress;

    // ---------------- 基本信息 end ---------------------

    // ---------------- 经营信息 start ---------------------

    @ExcelProperty("员工数")
    private String employeeMaxCount;

    @ExcelProperty("经营规模")
    private String newManageScaleName;

    @ExcelProperty("所属区域省")
    private String businessProvinceName;

    @ExcelProperty("所属区域市")
    private String businessCityName;

    @ExcelProperty("所属区域区县")
    private String businessAreaName;

    // ---------------- 经营信息 end ---------------------
}