package com.maple.demo.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;

import javax.servlet.ServletOutputStream;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.maple.demo.util.PdfFontUtil.*;

/**
 * @author 笑小枫
 * @date 2022/8/15
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
@Slf4j
public class PdfUtil {

    private PdfUtil() {

    }

    /**
     * 利用模板生成pdf
     *
     * @param data         写入的数据
     * @param photoMap     图片信息
     * @param out          自定义保存pdf的文件流
     * @param templatePath pdf模板路径
     */
    public static void fillTemplate(Map<String, Object> data, Map<String, String> photoMap, ServletOutputStream out, String templatePath) {
        PdfReader reader;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {
            // 读取pdf模板
            reader = new PdfReader(templatePath);
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields acroFields = stamper.getAcroFields();
            // 赋值
            for (String name : acroFields.getFields().keySet()) {
                String value = data.get(name) != null ? data.get(name).toString() : null;
                acroFields.setField(name, value);
            }

            // 图片赋值
            for (Map.Entry<String, String> entry : photoMap.entrySet()) {
                if (Objects.isNull(entry.getKey())) {
                    continue;
                }
                String key = entry.getKey();
                String url = entry.getValue();
                // 根据地址读取需要放入pdf中的图片
                Image image = Image.getInstance(url);
                // 设置图片在哪一页
                PdfContentByte overContent = stamper.getOverContent(acroFields.getFieldPositions(key).get(0).page);
                // 获取模板中图片域的大小
                Rectangle signRect = acroFields.getFieldPositions(key).get(0).position;
                float x = signRect.getLeft();
                float y = signRect.getBottom();
                // 图片等比缩放
                image.scaleAbsolute(signRect.getWidth(), signRect.getHeight());
                // 图片位置
                image.setAbsolutePosition(x, y);
                // 在该页加入图片
                overContent.addImage(image);
            }

            // 如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.setFormFlattening(true);
            stamper.close();
            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();
            bos.close();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建PDF，并导出
     *
     * @param out 保存路径
     */
    public static void createAndExportPdfPage(ServletOutputStream out) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // 创建文档
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            document.open();
            // 报告标题
            document.add(PdfFontUtil.getParagraph("笑小枫的网站介绍", TITLE_FONT, 1));
            document.add(PdfFontUtil.getParagraph("\n网站名称：笑小枫(www.xiaoxiaofeng.com)", INFO_FONT, -1));
            document.add(PdfFontUtil.getParagraph("\n生成时间：2022-07-02\n\n", INFO_FONT, -1));
            // 报告内容
            // 段落标题 + 报表图
            document.add(PdfFontUtil.getParagraph("文章数据统计", NODE_FONT, -1));
            document.add(PdfFontUtil.getParagraph("\n· 网站首页图\n\n", BLOCK_FONT, -1));
            // 设置图片宽高
            float documentWidth = document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin();
            float documentHeight = documentWidth / 580 * 320;
            document.add(PdfFontUtil.getImage("D:\\xiaoxiaofeng.jpg", documentWidth - 80, documentHeight - 80));
            // 数据表格
            document.add(PdfFontUtil.getParagraph("\n· 数据详情\n\n", BLOCK_FONT, -1));
            // 生成6列的表格
            PdfPTable dataTable = PdfFontUtil.getPdfTable(6, 500);
            // 设置表格
            List<String> tableHeadList = tableHead();
            List<List<String>> tableDataList = getTableData();
            PdfFontUtil.addTableCell(dataTable, CONTENT_FONT, tableHeadList);
            for (List<String> tableData : tableDataList) {
                PdfFontUtil.addTableCell(dataTable, CONTENT_FONT, tableData);
            }
            document.add(dataTable);
            document.add(PdfFontUtil.getParagraph("\n· 报表描述\n\n", BLOCK_FONT, -1));
            document.add(PdfFontUtil.getParagraph("数据报告可以监控每天的推广情况，" +
                    "可以针对不同的数据表现进行分析，以提升推广效果。", CONTENT_FONT, -1));
            document.newPage();
            document.close();
            baos.writeTo(out);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建PDF，并保存到指定位置
     *
     * @param filePath 保存路径
     */
    public static void createPdfPage(String filePath) {
        // FileOutputStream 需要关闭，释放资源
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            // 创建文档
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();
            // 报告标题
            document.add(PdfFontUtil.getParagraph("笑小枫的网站介绍", TITLE_FONT, 1));
            document.add(PdfFontUtil.getParagraph("\n网站名称：笑小枫(www.xiaoxiaofeng.com)", INFO_FONT, -1));
            document.add(PdfFontUtil.getParagraph("\n生成时间：2022-07-02\n\n", INFO_FONT, -1));
            // 报告内容
            // 段落标题 + 报表图
            document.add(PdfFontUtil.getParagraph("文章数据统计", NODE_FONT, -1));
            document.add(PdfFontUtil.getParagraph("\n· 网站首页图\n\n", BLOCK_FONT, -1));
            // 设置图片宽高
            float documentWidth = document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin();
            float documentHeight = documentWidth / 580 * 320;
            document.add(PdfFontUtil.getImage("D:\\xiaoxiaofeng.jpg", documentWidth - 80, documentHeight - 80));
            // 数据表格
            document.add(PdfFontUtil.getParagraph("\n· 数据详情\n\n", BLOCK_FONT, -1));
            // 生成6列的表格
            PdfPTable dataTable = PdfFontUtil.getPdfTable(6, 500);
            // 设置表格
            List<String> tableHeadList = tableHead();
            List<List<String>> tableDataList = getTableData();
            PdfFontUtil.addTableCell(dataTable, CONTENT_FONT, tableHeadList);
            for (List<String> tableData : tableDataList) {
                PdfFontUtil.addTableCell(dataTable, CONTENT_FONT, tableData);
            }
            document.add(dataTable);
            document.add(PdfFontUtil.getParagraph("\n· 报表描述\n\n", BLOCK_FONT, -1));
            document.add(PdfFontUtil.getParagraph("数据报告可以监控每天的推广情况，" +
                    "可以针对不同的数据表现进行分析，以提升推广效果。", CONTENT_FONT, -1));
            document.newPage();
            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 模拟数据
     */
    private static List<String> tableHead() {
        List<String> tableHeadList = new ArrayList<>();
        tableHeadList.add("省份");
        tableHeadList.add("城市");
        tableHeadList.add("数量");
        tableHeadList.add("百分比1");
        tableHeadList.add("百分比2");
        tableHeadList.add("百分比3");
        return tableHeadList;
    }

    /**
     * 模拟数据
     */
    private static List<List<String>> getTableData() {
        List<List<String>> tableDataList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<String> tableData = new ArrayList<>();
            tableData.add("浙江" + i);
            tableData.add("杭州" + i);
            tableData.add("276" + i);
            tableData.add("33.3%");
            tableData.add("34.3%");
            tableData.add("35.3%");
            tableDataList.add(tableData);
        }
        return tableDataList;
    }

    /**
     * 合并pdf文件
     *
     * @param files   要合并文件数组(绝对路径如{ "D:\\test\\1.pdf", "D:\\test\\2.pdf" , "D:\\test\\3.pdf"})
     * @param newFile 合并后存放的目录D:\\test\\xxf-merge.pdf
     * @return boolean 生成功返回true, 否則返回false
     */
    public static boolean mergePdfFiles(String[] files, String newFile) {
        boolean retValue = false;
        Document document;
        try (FileOutputStream fileOutputStream = new FileOutputStream(newFile)) {
            document = new Document(new PdfReader(files[0]).getPageSize(1));
            PdfCopy copy = new PdfCopy(document, fileOutputStream);
            document.open();
            for (String file : files) {
                PdfReader reader = new PdfReader(file);
                int n = reader.getNumberOfPages();
                for (int j = 1; j <= n; j++) {
                    document.newPage();
                    PdfImportedPage page = copy.getImportedPage(reader, j);
                    copy.addPage(page);
                }
            }
            retValue = true;
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retValue;
    }

    /**
     * 读取PDF，读取后删除PDF，适用于生成后需要导出PDF，创建临时文件
     */
    public static void readDeletePdf(String fileName, ServletOutputStream outputStream) {
        File file = new File(fileName);
        if (!file.exists()) {
            log.info(fileName + "文件不存在");
        }
        try (InputStream in = new FileInputStream(fileName)) {
            IOUtils.copy(in, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Files.delete(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
