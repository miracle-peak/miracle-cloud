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
import javax.servlet.http.HttpServletRequest;
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
     * @param id         识别jwt唯一id
     * @param userName   jwt签名用户名
     * @param expireTime jwt过期时间 Date类型
     * @return
     */
    public static String createJwt(String id, String userName, Date expireTime) {
        Map<String, Object> info = new HashMap<>(6);

        info.put(GlobalConstant.JWT_ID, id);
        info.put("userName", userName);
        // 加密秘钥
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

        validate.setSuccess(false);
        try {
            claims = parseJwt(jwt);
            if (!claims.containsKey(GlobalConstant.JWT_ID)) {
                log.error("可能存在伪造token，无 id key");
                return validate;
            }
            validate.setSuccess(true);
            validate.setClaims(claims);
        } catch (ExpiredJwtException e) {
            validate.setErrCode(GlobalConstant.JWT_EXPIRE);
            log.warn("jwt过期:{}", e.getMessage());
        } catch (Exception e) {
            log.error("jwt Exception--->" + e.getMessage());
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
     * 根据jwt获取当前用户的userId
     * 前端请求时将jwt放入请求头 Authorization 做为key
     *
     * @param request
     * @return
     */
    public static String getUserIdByJwt(HttpServletRequest request) {
        String jwt = request.getHeader(GlobalConstant.HEADER_JWT);
        // 如果head的Authorization没有则从cookie中取
        if (StringUtils.isEmpty(jwt)) {
            log.warn("请求头的Authorization中没有得到jwt");
            // TODO 如果加上从cookie中获取jwt,在当前浏览器发送任何请求都会带上cookie即不能防止CSRF攻击
            // TODO 前端使用代理且本地保存了cookie
//            Cookie[] cookies = request.getCookies();
//            if(cookies.length == 0){
            throw new RuntimeException("获取不到您的身份或身份认证过期,请重新登录");
//            }
//            jwt = cookies[0].getValue();
        }
        Claims claims = null;
        try {
            claims = JwtUtil.parseJwt(jwt);
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("身份认证过期请重新登录");
        }
        if (!claims.containsKey(GlobalConstant.JWT_ID)) {
            log.error("没有 userId对应的key应该是伪造的jwt");
            throw new RuntimeException("对不起！您的token不可用");
        }

        return claims.get(GlobalConstant.JWT_ID).toString();
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
