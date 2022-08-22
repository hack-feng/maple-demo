package com.maple.demo.util.common;

import com.alibaba.fastjson.JSON;
import com.maple.demo.config.bean.ErrorCode;
import com.maple.demo.config.exception.MapleCommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA加解密处理工具类
 *
 * @author 笑小枫
 * @date 2022/7/20
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
@Slf4j
public class RsaUtil {

    /**
     * 指定加密算法RSA非对称加密
     */
    private static final String RSA = "RSA";

    /**
     * 指定RSA非对称算法/ECB模式/填充方式
     * 默认填充方式：RSA/ECB/PKCS1Padding
     */
    private static final String RSA_CIPHER = "RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING";

    public static void main(String[] args) {
        Map<String, String> keys = createKeys();
        log.info("密钥对：" + JSON.toJSONString(keys));
        try {
            String ciphertext = publicEncrypt("123abcABC", keys.get("publicKey"));
            log.info("加密后的数据：" + ciphertext);
            String original = privateDecrypt(ciphertext, keys.get("privateKey"));
            log.info("解密后的原文：" + original);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static Map<String, String> createKeys() {
        //为RSA算法创建一个KeyPairGenerator对象（KeyPairGenerator，密钥对生成器，用于生成公钥和私钥对）
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(RSA);
        } catch (NoSuchAlgorithmException e) {
            log.error("获取公钥私钥发生错误", e);
            throw new IllegalArgumentException("No such algorithm-->[" + RSA + "]");
        }

        //初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(2048);
        //生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        //得到公钥
        Key publicKey = keyPair.getPublic();
        //返回一个publicKey经过二次加密后的字符串
        String publicKeyStr = Base64Utils.encodeToString(publicKey.getEncoded());
        //得到私钥
        Key privateKey = keyPair.getPrivate();
        //返回一个privateKey经过二次加密后的字符串
        String privateKeyStr = Base64Utils.encodeToString(privateKey.getEncoded());

        Map<String, String> keyPairMap = new HashMap<>(16);
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);
        return keyPairMap;
    }

    /**
     * 得到公钥
     *
     * @param publicKey 密钥字符串（经过base64编码）
     */
    private static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64Utils.decodeFromString(publicKey));
        return (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
    }

    /**
     * 得到私钥
     *
     * @param privateKey 密钥字符串（经过base64编码）
     */
    private static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64Utils.decodeFromString(privateKey));
        return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
    }

    /**
     * 公钥加密
     */
    public static String publicEncrypt(String data, String publicKey) {
        try {
            byte[] dataByte = data.getBytes(StandardCharsets.UTF_8);
            Cipher cipher = Cipher.getInstance(RSA_CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
            return Base64Utils.encodeToString(cipher.doFinal(dataByte));
        } catch (Exception e) {
            log.error("加密字符串遇到异常", e);
            throw new MapleCommonException(ErrorCode.COMMON_ERROR, "加密字符串遇到异常");
        }
    }

    /**
     * 私钥解密
     */
    public static String privateDecrypt(String data, String privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
            return new String(cipher.doFinal(Base64Utils.decodeFromString(data)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("解密字符串遇到异常", e);
            throw new MapleCommonException(ErrorCode.COMMON_ERROR, "解密字符串遇到异常");
        }
    }
}
