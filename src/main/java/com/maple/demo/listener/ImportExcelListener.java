package com.maple.demo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.util.ListUtils;
import com.maple.demo.config.bean.excel.CommonExcel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author 笑小枫
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 * @date 2022/7/22
 */
@Slf4j
public class ImportExcelListener<T> implements ReadListener<T> {

    /**
     * 默认一次读取1000条，可根据实际业务和服务器调整
     */
    private static final int BATCH_COUNT = 1000;
    /**
     * Temporary storage of data
     */
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private final List<String> errorMsgList;

    /**
     * consumer
     */
    private final Consumer<List<T>> consumer;

    public ImportExcelListener(Consumer<List<T>> consumer, List<String> errorMsgList) {
        this.consumer = consumer;
        this.errorMsgList = errorMsgList;
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        // 记录行号
        if (data instanceof CommonExcel) {
            ReadRowHolder readRowHolder = context.readRowHolder();
            ((CommonExcel) data).setRowIndex(readRowHolder.getRowIndex() + 1);
        }
        cachedDataList.add(data);
        if (cachedDataList.size() >= BATCH_COUNT) {
            consumer.accept(cachedDataList);
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (CollectionUtils.isNotEmpty(cachedDataList)) {
            consumer.accept(cachedDataList);
        }
    }

    /**
     * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        // 如果是某一个单元格的转换异常 能获取到具体行号
        String errorMsg = String.format("%s， 第%d行解析异常", context.readSheetHolder().getReadSheet().getSheetName(),
                context.readRowHolder().getRowIndex() + 1);
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            errorMsg = String.format("第%d行，第%d列数据解析异常",
                    excelDataConvertException.getRowIndex() + 1,
                    excelDataConvertException.getColumnIndex() + 1);
            log.error("{}， 第{}行，第{}列解析异常，数据为:{}",
                    context.readSheetHolder().getReadSheet().getSheetName(),
                    excelDataConvertException.getRowIndex() + 1,
                    excelDataConvertException.getColumnIndex() + 1,
                    excelDataConvertException.getCause().getMessage());
        } else {
            log.error(errorMsg + exception.getMessage());
        }
        errorMsgList.add(errorMsg);
    }
}