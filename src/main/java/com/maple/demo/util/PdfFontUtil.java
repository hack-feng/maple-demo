package com.maple.demo.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.IOException;
import java.util.List;

/**
 * @author 笑小枫
 * @date 2022/8/15
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
public class PdfFontUtil {

    private PdfFontUtil() {
    }

    /**
     * 基础配置，可以放相对路径，这里演示绝对路径，因为字体文件过大，这里不传到项目里面了，需要的自己下载
     * 下载地址：https://www.zitijia.com/downloadpage?itemid=281258939050380345
     */
    public static final String FONT = "D:\\font/Simhei.ttf";

    /**
     * 基础样式
     */
    public static final Font TITLE_FONT = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, 20, Font.BOLD);
    public static final Font NODE_FONT = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, 15, Font.BOLD);
    public static final Font BLOCK_FONT = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, 13, Font.BOLD, BaseColor.BLACK);
    public static final Font INFO_FONT = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, 12, Font.NORMAL, BaseColor.BLACK);
    public static final Font CONTENT_FONT = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);


    /**
     * 段落样式获取
     */
    public static Paragraph getParagraph(String content, Font font, Integer alignment) {
        Paragraph paragraph = new Paragraph(content, font);
        if (alignment != null && alignment >= 0) {
            paragraph.setAlignment(alignment);
        }
        return paragraph;
    }

    /**
     * 图片样式
     */
    public static Image getImage(String imgPath, float width, float height) throws IOException, BadElementException {
        Image image = Image.getInstance(imgPath);
        image.setAlignment(Image.MIDDLE);
        if (width > 0 && height > 0) {
            image.scaleAbsolute(width, height);
        }
        return image;
    }

    /**
     * 表格生成
     */
    public static PdfPTable getPdfTable(int numColumns, float totalWidth) {
        // 表格处理
        PdfPTable table = new PdfPTable(numColumns);
        // 设置表格宽度比例为%100
        table.setWidthPercentage(100);
        // 设置宽度:宽度平均
        table.setTotalWidth(totalWidth);
        // 锁住宽度
        table.setLockedWidth(true);
        // 设置表格上面空白宽度
        table.setSpacingBefore(10f);
        // 设置表格下面空白宽度
        table.setSpacingAfter(10f);
        // 设置表格默认为无边框
        table.getDefaultCell().setBorder(0);
        table.setPaddingTop(50);
        table.setSplitLate(false);
        return table;
    }

    /**
     * 表格内容带样式
     */
    public static void addTableCell(PdfPTable dataTable, Font font, List<String> cellList) {
        for (String content : cellList) {
            dataTable.addCell(getParagraph(content, font, -1));
        }
    }
}
