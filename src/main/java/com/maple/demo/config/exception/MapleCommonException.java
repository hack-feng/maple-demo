package com.maple.demo.config.exception;

import com.maple.demo.config.bean.ErrorCode;

/**
 * 通用异常，偷懒就抛出此异常吧
 *
 * @author 笑小枫
 * @date 2022/07/20
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
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
