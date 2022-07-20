package com.maple.demo.config.bean;

/**
 * @author 笑小枫
 * @date 2022/7/20
 */
public class GlobalConfig {

    private GlobalConfig() {

    }

    /**
     * 用户储存在redis中的过期时间
     */
    public static final long EXPIRE_TIME = 60 * 60 * 12L;

    /**
     * 生成token的私钥，数据库预留了salt字段，可以每个用户创建时生成不同的私钥，数据安全性更高，这里为了方便，暂不演示了
     */
    public static final String SECRET = "maple1223";
    
    public static final String TOKEN_NAME = "Authorization";

    /**
     * 用户登录token保存在redis的key值
     *
     * @param account 用户登录帐号
     * @return token保存在redis的key
     */
    public static String getRedisUserKey(String account) {
        return "MAPLE_ADMIN:" + account;
    }
}
