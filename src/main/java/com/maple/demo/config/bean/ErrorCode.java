package com.maple.demo.config.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 统一异常信息枚举类
 *
 * @author 笑小枫
 * @date 2021/12/9
 */
@AllArgsConstructor
@Getter
public enum ErrorCode {

    /**
     * 异常信息
     */
    PARAM_ERROR("9001", "请求参数有误，请重试"),

    /**
     * 抛出此异常码，请重新在ErrorMsg定义msg
     */
    COMMON_ERROR("9998", "笑小枫太懒，居然没有定义异常原因"),

    OTHER_ERROR("9999", "未知异常，请联系笑小枫：https://www.xiaoxiaofeng.site");

    private final String code;

    private final String msg;
}