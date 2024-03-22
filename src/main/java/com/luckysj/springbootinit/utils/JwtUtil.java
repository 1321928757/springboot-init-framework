package com.luckysj.springbootinit.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @description: Jwt工具类，生成JWT和认证
 * @author: kitie
 */
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private static final String SECRET = "my_secret";
    /**Base64编码的密钥*/
    private static final String base64EncodedSecretKey = Base64.encodeBase64String(SECRET.getBytes());
    private static final Algorithm algorithm = Algorithm.HMAC256(Base64.decodeBase64(Base64.encodeBase64String(SECRET.getBytes())));



    /**
    * @description 生成Jwt令牌
    * @param issuer JWT签发者名称，可为当前应用名
    * @param ttlMillis 令牌有效期时长(ms)
    * @param claims 自定义声明，可以在这存放一些数据
    * @return JWT令牌
    * @date 2024/01/06 13:43:25
    */
    public static String createToken(String issuer, long ttlMillis, Map<String, Object> claims) {
        logger.info("开始生成JWT令牌，颁发者{}, 有效期{}", issuer, ttlMillis);

        // 计算过期时间
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + ttlMillis;
        Date exp = new Date(expMillis);

        JwtBuilder builder = Jwts.builder()
                // 荷载部分
                .setClaims(claims)
                // 这个是JWT的唯一标识，一般设置成唯一的
                .setId(UUID.randomUUID().toString())//2.
                // 签发时间
                .setIssuedAt(new Date(nowMillis))
                // 过期时间
                .setExpiration(exp)
                // 签发人，也就是JWT是给谁的（逻辑上一般都是username或者userId）
                .setSubject(issuer)
                .signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey);//这个地方是生成jwt使用的算法和秘钥
        return builder.compact();
    }

    /**
    * @description 解析JWT，获取claim信息
    * @param jwtToken jwt令牌
    * @return Claims claim信息
    * @date 2024/01/06 14:00:39
    */
    public static Claims parseToken(String jwtToken) {
        // 得到 DefaultJwtParser
        return Jwts.parser()
                // 设置签名的秘钥
                .setSigningKey(base64EncodedSecretKey)
                // 设置需要解析的 jwt
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    // 判断jwtToken是否合法
    public static boolean isVerify(String jwtToken) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(jwtToken);
            // 校验不通过会抛出异常
            // 判断合法的标准：1. 头部和荷载部分没有篡改过。2. 没有过期
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("jwt 校验出错，原因:", e);
            return false;
        }
    }
}
