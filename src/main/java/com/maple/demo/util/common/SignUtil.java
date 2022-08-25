package com.maple.demo.util.common;

import com.maple.demo.config.bean.ErrorCode;
import com.maple.demo.config.exception.MapleCheckException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 签名处理工具类
 * 格式sign_yyyyMMddHHmmss
 * sign：加密后的数据
 * _：用于区分数据，方便后端分割处理，当然也可以使用两个参数传输
 * yyyyMMddHHmmss：当前时间，用于验证签名有效时间
 * <p>
 * 其中sign=SHA256(data&yyyyMMddHHmmss&salt)
 * data：建议放唯一的业务数据
 * yyyyMMddHHmmss：和上文的时间要一致
 * salt：盐值，可随机生产，妥善保管
 *
 * @author 笑小枫
 * @date 2022/7/20
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
@Slf4j
public class SignUtil {

    private SignUtil() {
    }

    public static void main(String[] args) {
        String data = "XXF001";
        String date = DateUtil.dateToStr(new Date(), DateUtil.YYYYMMDDHHMMSS);
        String salt = "d84d9e1fe49d427da66fd78724dcfc29";
        String ciphertext = DigestUtils.sha256Hex(data + "&" + date + "&" + salt);
        log.info("加密后的密文：" + ciphertext);
        checkCangoSign(data, ciphertext + "_" + date, salt, 300L);
    }

    /**
     * 灿谷签名验证
     * 格式sign_yyyyMMddHHmmss
     * 其中sign=sha256Hex(unionId&yyyyMMddHHmmss&salt)
     *
     * @param businessData 业务数据
     * @param sign         签名信息
     * @param salt         加密盐值
     * @param expireSecond 有效期(秒)
     */
    public static void checkCangoSign(String businessData, String sign, String salt, Long expireSecond) {
        if (StringUtils.isBlank(businessData) || StringUtils.isBlank(sign)) {
            log.error(String.format("验签失败，businessData或sign为空，businessData：%s；sign：%s", businessData, sign));
            throw new MapleCheckException(ErrorCode.COMMON_ERROR, "参数有误，请检查后重试");
        }
        String[] signArray = sign.split("_");
        // 签名拆分后为两部分
        int singNum = 2;

        Date reqDate = DateUtil.strToDate(signArray[1], DateUtil.YYYYMMDDHHMMSS);
        Date nowDate = new Date();

        if (reqDate == null || (signArray.length != singNum || secondBetween(reqDate, nowDate) > expireSecond)) {
            log.error(String.format("验签失败，sign无效，businessData：%s；sign：%s", businessData, sign));
            throw new MapleCheckException(ErrorCode.COMMON_ERROR, "sign无效");
        }

        if (secondBetween(nowDate, reqDate) > expireSecond) {
            log.error(String.format("验签失败，sign已过期，businessData：%s；sign：%s", businessData, sign));
            throw new MapleCheckException(ErrorCode.COMMON_ERROR, "sign已过期，请重试");
        }
        String checkDataSha256 = DigestUtils.sha256Hex(businessData + "&" + signArray[1] + "&" + salt);
        if (!signArray[0].equalsIgnoreCase(checkDataSha256)) {
            log.error(String.format("验签失败，签名验证失败，businessData：%s；sign：%s", businessData, sign));
            throw new MapleCheckException(ErrorCode.COMMON_ERROR, "签名验证失败");
        }
    }

    /**
     * 获取两个时间之间相差的秒数
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差的时间（秒）
     */
    private static long secondBetween(Date startDate, Date endDate) {
        return (startDate.getTime() - endDate.getTime()) / 1000;
    }
}
