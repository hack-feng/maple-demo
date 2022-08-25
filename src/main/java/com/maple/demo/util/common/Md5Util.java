package com.maple.demo.util.common;

import com.maple.demo.config.bean.ErrorCode;
import com.maple.demo.config.exception.MapleCommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;


/**
 * MD5撒盐加密 及MD5加密
 *
 * @author 笑小枫
 * @date 2022/7/20
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
@Slf4j
public class Md5Util {

    private Md5Util() {

    }

    public static void main(String[] args) {
        String password = "123456";
        log.info("普通MD5加密：" + md5(password));
        String salt = "xxx";
        String cipherText = encrypt(password, salt);
        log.info("MD5加盐加密后的密文：" + cipherText);
        boolean isOk = verifyPassword(password, cipherText, salt);
        log.info("校验密码结果：" + isOk);
    }

    /**
     * MD5加密处理
     *
     * @param password 密码明文
     * @return 加密后密文
     */
    public static String md5(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    /**
     * 密码加密处理
     *
     * @param password 密码明文
     * @param salt     盐
     * @return 加密后密文
     */
    public static String encrypt(String password, String salt) {
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(salt)) {
            log.error("密码加密失败原因： password and salt cannot be empty");
            throw new MapleCommonException(ErrorCode.PARAM_ERROR);
        }
        return DigestUtils.md5DigestAsHex((salt + password).getBytes());
    }

    /**
     * 校验密码
     *
     * @param target 待校验密码
     * @param source 原密码
     * @param salt   加密原密码的盐
     */
    public static boolean verifyPassword(String target, String source, String salt) {
        if (StringUtils.isEmpty(target) || StringUtils.isEmpty(source) || StringUtils.isEmpty(salt)) {
            log.info("校验密码失败，原因 target ={}， source ={}， salt ={}", target, source, salt);
            return false;
        }
        String targetEncryptPwd = encrypt(target, salt);
        return targetEncryptPwd.equals(source);
    }
}
