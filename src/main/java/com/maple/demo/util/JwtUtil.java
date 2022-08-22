package com.maple.demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.maple.demo.config.bean.ErrorCode;
import com.maple.demo.config.bean.GlobalConfig;
import com.maple.demo.config.bean.TokenBean;
import com.maple.demo.config.exception.MapleCheckException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Jwt常用操作
 *
 * @author 笑小枫
 * @date 2022/7/20
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
public class JwtUtil {

    private static final String ACCOUNT = "account";
    private static final String USER_ID = "userId";
    private static final String USER_TYPE = "userType";

    /**
     * 校验token是否正确
     *
     * @param token 密钥
     * @return 是否正确
     */
    public static boolean verify(String token, String account) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(GlobalConfig.SECRET);
            JWTVerifier verifier = JWT.require(algorithm).withClaim(ACCOUNT, account).build();
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户登录帐号
     */
    public static String getAccount() {
        try {
            DecodedJWT jwt = getJwt();
            if (jwt == null) {
                return null;
            }
            return jwt.getClaim(ACCOUNT).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户登录帐号
     */
    public static String getAccount(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(ACCOUNT).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static Long getUserId() {
        try {
            DecodedJWT jwt = getJwt();
            if (jwt == null) {
                return null;
            }
            return jwt.getClaim(USER_ID).asLong();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户ID
     */
    public static Long getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(USER_ID).asLong();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static TokenBean getTokenMsg() {
        TokenBean tokenBean = new TokenBean();
        try {
            DecodedJWT jwt = getJwt();
            if (jwt == null) {
                return tokenBean;
            }
            tokenBean.setUserId(jwt.getClaim(USER_ID).asLong());
            tokenBean.setAccount(jwt.getClaim(ACCOUNT).asString());
            return tokenBean;

        } catch (JWTDecodeException e) {
            return tokenBean;
        }
    }

    private static DecodedJWT getJwt() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(servletRequestAttributes)) {
            throw new MapleCheckException(ErrorCode.PARAM_ERROR);
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();

        String authorization = request.getHeader(GlobalConfig.TOKEN_NAME);
        if (authorization == null) {
            return null;
        }
        return JWT.decode(authorization);
    }

    /**
     * 校验token是否有效
     *
     * @param token token信息
     * @return 返回结果
     */
    public static boolean verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(GlobalConfig.SECRET)).build();
            DecodedJWT jwt = verifier.verify(token);
            jwt.getClaims();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 创建token
     *
     * @param tokenBean token保存的信息
     * @return token
     */
    public static String createToken(TokenBean tokenBean) {
        Algorithm algorithm = Algorithm.HMAC256(GlobalConfig.SECRET);
        return JWT.create()
                .withClaim(USER_ID, tokenBean.getUserId())
                .withClaim(ACCOUNT, tokenBean.getAccount())
                .withClaim(USER_TYPE, tokenBean.getUserType())
                .sign(algorithm);
    }
}
