package com.maple.demo.config.exception;

import com.maple.demo.config.bean.ErrorCode;

/**
 * 检测结果不一致时，抛出此异常
 *
 * @author 笑小枫
 * @date 2022/07/15
 */
public class MapleCommonException extends MapleBaseException {

    public MapleCommonException(String code, String errorMsg) {
        super(code, errorMsg);
    }

    public MapleCommonException(ErrorCode code) {
        super(code);
    }

    public MapleCommonException(ErrorCode code, String errorMsg) {
        super(code, errorMsg);
    }
}
