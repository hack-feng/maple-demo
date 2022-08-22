package com.maple.demo.config.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.thymeleaf.context.Context;

import java.util.List;

/**
 * @author 笑小枫
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 * @date 2022/7/22
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class EmailBean {
    /**
     * 填充内容
     */
    private Context context;

    /**
     * 使用模板，和text互斥，优先使用模板，模板不存在发送text内容
     */
    private String templateName;

    /**
     * 发送给谁
     */
    private String toUser;

    /**
     * 抄送给谁
     */
    private String[] ccUser;

    /**
     * 邮件主体
     */
    private String subject;

    /**
     * 邮件内容，和templateName互斥，优先使用模板，模板不存在发送text内容
     */
    private String text;

    /**
     * 附件列表
     */
    private List<String> attachmentList;
}
