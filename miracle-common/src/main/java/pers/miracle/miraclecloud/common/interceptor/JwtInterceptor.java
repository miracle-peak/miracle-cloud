package pers.miracle.miraclecloud.common.interceptor;

import io.jsonwebtoken.Claims;
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


/**
 * jwt拦截验证
 *
 * @author: 蔡奇峰
 * @date: 2020/3/25 13:28
 * @Version V1.0
 **/
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String jwt = request.getHeader(GlobalConstant.HEADER_JWT);

        if (!StringUtils.isEmpty(jwt)) {
            // 验证jwt
            Jwt validate = JwtUtil.validateJwt(jwt);
            // jwt验证不通过
            if (!validate.isSuccess()) {
                ResponseUtil.response("对不起！您的token 有误！validate result :token error",
                        validate.getErrCode(), response);
                return false;
            }
            // 获取解密的jwt
            Claims claims = validate.getClaims();
            String id = claims.get(GlobalConstant.JWT_ID).toString();
            // 获取redis中jwt
            String token = redisUtil.getToken(id);
            // jwt不一致或者redis中没有
            if (!jwt.equals(token)) {
                ResponseUtil.response("对不起！您的token 有误！token error",
                        GlobalConstant.TOKEN_ERROR, response);
                return false;
            }
            return true;
        }
        // 被拦截时也给出响应
        ResponseUtil.response("对不起！您木有权限！请尝试登录",
                GlobalConstant.TOKEN_NONE, response);

        return false;
    }


}
