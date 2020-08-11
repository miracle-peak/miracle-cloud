package pers.miracle.miraclecloud.common.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import pers.miracle.miraclecloud.common.constant.GlobalConstant;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 蔡奇峰
 * @Version V1.0
 **/
public class JwtUtil {
    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * 生成jwt
     *
     * @param id
     * @param userName
     * @param expireTime
     * @return
     */
    public static String createToken(String id, String userName, Date expireTime) {
        Map<String, Object> info = new HashMap<>(6);

        info.put("id", id);
        info.put("userName", userName);

        SecretKey key = getSecret();

        String jwt = Jwts.builder()
                .setClaims(info)
                .setIssuer(userName)
                .setSubject("miracle")
                .setIssuedAt(new Date())
                .setExpiration(expireTime)
                .signWith(SignatureAlgorithm.HS256, key)
//                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();

        return jwt;
    }

    /**
     * 验证jwt
     *
     * @param jwt
     * @return
     */
    public static Jwt validateJwt(String jwt) {

        Jwt validate = new Jwt();

        Claims claims = null;

        try {
            claims = parseJwt(jwt);
            validate.setSuccess(true);
            validate.setClaims(claims);
        } catch (ExpiredJwtException e) {
            validate.setSuccess(false);
            validate.setErrCode(GlobalConstant.JWT_EXPIRE);
            log.warn("jwt过期:{}", e.getMessage());
        } catch (Exception e) {
            log.error("jwt Exception--->" + e.getMessage());
            validate.setSuccess(false);
            validate.setErrCode(GlobalConstant.JWT_EXCEPTION);
        }

        return validate;
    }


    /**
     * 解密jwt
     *
     * @param jwt
     * @return
     */
    public static Claims parseJwt(String jwt) {
        SecretKey key = getSecret();

        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }


    /**
     * 获取加密秘钥
     *
     * @return
     */
    public static SecretKey getSecret() {
        SecretKey key = new SecretKeySpec(GlobalConstant.SECRET.getBytes(), 0,
                GlobalConstant.SECRET.length(), "AES");

        return key;
    }

}
