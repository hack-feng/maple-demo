package com.maple.demo.config.bean.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import lombok.Data;

/**
 * @author 笑小枫
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
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
