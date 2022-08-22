package com.maple.demo.config.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 笑小枫
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 * @date 2022/7/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 40)
@HeadFontStyle(fontHeightInPoints = 12)
@ContentFontStyle(fontHeightInPoints = 11)
@ColumnWidth(20)
public class ExportCompany {

    // -------------------- 基本信息 start -------------

    @ExcelProperty({"基本信息", "公司名称"})
    private String companyName;

    @ExcelProperty({"基本信息", "省份"})
    private String province;

    @ExcelProperty({"基本信息", "成立时间"})
    private Date startDate;

    @ExcelProperty({"基本信息", "企业状态"})
    private String entStatus;

    @ColumnWidth(30)
    @ExcelProperty({"基本信息", "博客地址"})
    private String csdnAddress;

    // ---------------- 基本信息 end ---------------------

    // ---------------- 经营信息 start ---------------------

    @ExcelProperty({"经营信息", "员工数"})
    private String employeeMaxCount;

    @ExcelProperty({"经营信息", "网站地址"})
    private String netAddress;

    @ExcelProperty({"经营信息", "所属区域省"})
    private String businessProvinceName;

    @ExcelProperty({"经营信息", "所属区域市"})
    private String businessCityName;

    @ExcelProperty({"经营信息", "所属区域区县"})
    private String businessAreaName;

    // ---------------- 经营信息 end ---------------------
}
