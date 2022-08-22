package com.maple.demo.util;

import com.maple.demo.config.bean.EmailBean;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author 笑小枫
 * @date 2022/7/22
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
@Service
@RequiredArgsConstructor
public class EmailUtil {

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    @Async
    public void sendEmail(EmailBean emailBean) {
        try {
            // 解决附件名称过长导致的附件名称乱码问题
            System.setProperty("mail.mime.splitlongparameters", "false");
            // 定义邮件信息
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(emailBean.getToUser());
            helper.setSubject(emailBean.getSubject());
            if (emailBean.getCcUser() != null && emailBean.getCcUser().length > 0) {
                helper.setCc(emailBean.getCcUser());
            }

            // 如果存在模板，定义邮件模板中的内容，context的内容对应email.html的${project}占位的内容
            if (emailBean.getContext() != null && StringUtils.isNotBlank(emailBean.getTemplateName())) {
                String emailContent = templateEngine.process(emailBean.getTemplateName(), emailBean.getContext());
                helper.setText(emailContent, true);
            } else {
                helper.setText(emailBean.getText());
            }

            // 如果存在附件，定义邮件的附件
            if (emailBean.getAttachmentList() != null && !emailBean.getAttachmentList().isEmpty()) {
                for (String attachment : emailBean.getAttachmentList()) {
                    FileSystemResource file = new FileSystemResource(attachment);
                    if (StringUtils.isNotBlank(file.getFilename())) {
                        helper.addAttachment(file.getFilename(), file);
                    }

                }
            }
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
