package com.maple.demo.config.bean.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import lombok.Data;

/**
 * @author 笑小枫
 * @date 2022/7/22
 */
@Data
public class CommonExcel {
    
    /**
     * 行号
     */
    @ExcelIgnore
    private Integer rowIndex;

}
