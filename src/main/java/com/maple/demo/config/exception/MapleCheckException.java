package com.maple.demo.config.exception;

import com.maple.demo.config.bean.ErrorCode;

/**
 * 检测结果不一致时，抛出此异常
 *
 * @author 笑小枫
 * @date 2022/07/15
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
public class MapleCheckException extends MapleBaseException {

    public MapleCheckException(String code, String errorMsg) {
        super(code, errorMsg);
    }

    public MapleCheckException(ErrorCode code) {
        super(code);
    }

    public MapleCheckException(ErrorCode code, String errorMsg) {
        super(code, errorMsg);
    }
}
