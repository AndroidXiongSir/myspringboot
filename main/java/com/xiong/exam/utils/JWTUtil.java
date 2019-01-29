package com.xiong.exam.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JWTUtil {

    // 过期时间1分钟
    public static final long EXPIRE_TIME = 10 * 60 * 1000;

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            Long userid = getUserId(token);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userid", userid)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (TokenExpiredException exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static Long getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userid").asLong();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,30min后过期
     *
     * @param userid 用户名
     * @param secret   用户的密码
     * @return 加密的token
     */
    public static String sign(Long userid, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create()
                .withClaim("userid", userid)
                .withExpiresAt(date)
                .sign(algorithm);
    }
}