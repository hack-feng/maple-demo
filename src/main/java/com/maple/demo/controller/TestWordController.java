package com.maple.demo.controller;

import com.maple.demo.util.common.Base64Util;
import com.maple.demo.util.WordUtil;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author 笑小枫
 * @date 2022/7/27
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/example")
@Api(tags = "实例演示-根据模板生成word")
public class TestWordController {
    
    /**
     * 文字处理：
     * 直接用${} 中间为替换的字段名。
     * 如果直接在word里面定义${title}，在转换成xml的时候有可能会被一些编码隔开，这个时候只需要用word打开xml，将这些内容重新输入一遍。
     * 可以用浏览器打开xml，检出自己定义的${}的内容是否都在一起，是否有被编码隔开的情况。
     * 图片处理：
     * 需要在word文档模版中插入图片
     * 将word转换成xml后，打开xml，会将我们的图片转换成长长的一段base64。
     * 我们把base64换成我们的${pic}就可以了，pic为字段名，可任意替换
     * 列表处理：
     * 需要在word文档模版中插入表格
     * 找到第二个<w:tr>，在其前面添加 <#list peopleList as list> 其中 peopleList是传入list的集合名称 list 是别名。
     * 参数取值为：${list.name}这样。
     * 在与<w:tr>配对的</w:tr>后面添加</#list>。 语法同freemaker的for循环语法
     */
    @GetMapping("/exportWord")
    public void exportWord(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1.创建临时文件夹
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        String fileName = UUID.randomUUID().toString().replace("-", "");

        // 处理图片信息，将图片转为base64字符串
        File imageFile = new File("D:\\xiaoxiaofeng.jpg");
        String base64 = Base64Util.fileToBase64(imageFile);

        // 处理表格的数据信息
        List<Map<String, Object>> list = new ArrayList<>();
        int size = 5;
        for (int i = 0; i < size; i++) {
            Map<String, Object> map = new HashMap<>(16);
            map.put("nickName", "笑小枫" + i);
            map.put("visitTime", "2022-07-27");
            map.put("isLike", "是");
            map.put("isAttention", "是");
            list.add(map);
        }

        // 加载word中的数据信息
        WordUtil word = new WordUtil();
        Map<String, Object> dataMap = new HashMap<>(16);
        dataMap.put("title", "记得关注我哟");
        dataMap.put("email", "1150640979@qq.com");
        dataMap.put("name", "笑小枫");
        dataMap.put("createDate", "2022-07-27");
        dataMap.put("pic", base64);
        dataMap.put("peopleList", list);
        word.createWord(dataMap, "word.xml", rootPath + "/" + fileName + ".doc");

        String exportName = "测试word";
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/msword");
        // 设置浏览器以下载的方式处理该文件名
        response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(exportName + ".doc", "UTF-8"))));

        File file = new File(rootPath + "/" + fileName + ".doc");
        try (InputStream fin = new FileInputStream(file);
             ServletOutputStream out = response.getOutputStream()) {
            // 缓冲区
            byte[] buffer = new byte[512];
            int bytesToRead;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 删除临时文件
            file.delete();
        }
    }
}
