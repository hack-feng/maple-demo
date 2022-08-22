package com.maple.demo.controller;

import com.maple.demo.config.bean.EmailBean;
import com.maple.demo.util.EmailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 笑小枫
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 * @date 2022/7/22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/example")
@Api(tags = "实例演示-发送邮件")
public class TestSendEmailController {

    private final EmailUtil emailUtil;

    @PostMapping("/sendEmailText")
    @ApiOperation(value = "发送纯文本带附件的邮件")
    public void sendEmailText(String text) {
        List<String> attachmentList = new ArrayList<>();
        // 定义绝对路径
        attachmentList.add("D:\\xiaoxiaofeng.jpg");
        // 定义相对路径
        attachmentList.add("src/main/resources/templates/email.html");

        EmailBean emailBean = EmailBean.builder()
                .text(text)
                .subject("欢迎使用笑小枫个人博客")
                .toUser("1150640979@qq.com")
                .attachmentList(attachmentList)
                .build();

        emailUtil.sendEmail(emailBean);
    }

    @PostMapping("/sendEmailTemplate")
    @ApiOperation(value = "根据html模板发送验证码邮件")
    public void sendEmailTemplate() {
        Context context = new Context();
        context.setVariable("project", "笑小枫个人博客");
        // 生成6位数字验证码
        String code = String.valueOf((int) (Math.random() * 900000 + 100000));
        context.setVariable("code", code);
        EmailBean emailBean = EmailBean.builder()
                .context(context)
                .templateName("email")
                .subject("笑小枫发送验证码")
                .toUser("1150640979@qq.com")
                .build();

        emailUtil.sendEmail(emailBean);
    }
}
