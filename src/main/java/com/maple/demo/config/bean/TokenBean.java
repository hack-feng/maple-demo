package com.maple.demo.config.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author 笑小枫
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 * @date 2022/7/20
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class TokenBean {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户类型
     */
    private String userType;
}
