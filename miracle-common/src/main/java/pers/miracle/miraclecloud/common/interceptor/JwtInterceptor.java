package pers.miracle.miraclecloud.common.interceptor;


import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import pers.miracle.miraclecloud.common.constant.GlobalConstant;
import pers.miracle.miraclecloud.common.utils.Jwt;
import pers.miracle.miraclecloud.common.utils.JwtUtil;
import pers.miracle.miraclecloud.common.utils.RedisUtil;
import pers.miracle.miraclecloud.common.utils.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt拦截验证
 *
 * @author: 蔡奇峰
 * @date: 2020/3/25 13:28
 * @Version V1.0
 **/
public class JwtInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);
    /**
     * 登录url
     */
    private static final String LOGIN_URL = "/user/login";

    /**
     * 注册url
     */
    private static final String REGISTER_URL = "/user/add";
    /**
     * 健康监控url
     */
    private static final String ACTUATOR_URL = "/actuator";

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uri = request.getRequestURI();

        // 不拦截登录,注册
        if (!uri.contains(LOGIN_URL) && !uri.contains(REGISTER_URL)
                && !uri.contains(ACTUATOR_URL)) {
            String jwt = request.getHeader(GlobalConstant.HEADER_JWT);

            Map<String, Object> resp = new HashMap<>(6);

            if (!StringUtils.isEmpty(jwt)) {
                // 验证jwt
                Jwt validate = JwtUtil.validateJwt(jwt);

                // jwt验证不通过
                if (!validate.isSuccess()) {
                    resp.put("message", "对不起！您的token 有误！validate result :token error");
                    resp.put("code", validate.getErrCode());

                    ResponseUtil.returnJson(response, resp);
                    return false;
                }

                // 解密jwt
                Claims claims = validate.getClaims();

                if (claims.containsKey(GlobalConstant.JWT_ID)) {
                    String id = claims.get(GlobalConstant.JWT_ID).toString();
                    // 获取redis中jwt
                    String token = redisUtil.getToken(id);
                    // jwt不一致
                    if (!jwt.equals(token)) {
                        resp.put("message", "对不起！您的token 有误！token error");
                        resp.put("code", GlobalConstant.TOKEN_ERROR);

                        ResponseUtil.returnJson(response, resp);

                        return false;
                    }
                    // 没有id, 可能是伪造的jwt
                } else {
                    log.error("可能存在伪造token，无 id key");
                    resp.put("message", "对不起！您的token 有误！token error");
                    resp.put("code", GlobalConstant.TOKEN_ERROR);

                    ResponseUtil.returnJson(response, resp);
                    return false;
                }
                // 被拦截时也给出响应
            } else {
                resp.put("message", "对不起！您木有权限！请尝试登录");
                resp.put("code", GlobalConstant.TOKEN_NONE);

                ResponseUtil.returnJson(response, resp);

                return false;
            }
        }

        return true;
    }

}
