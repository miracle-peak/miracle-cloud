package pers.miracle.miraclecloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pers.miracle.miraclecloud.common.utils.JwtUtil;
import pers.miracle.miraclecloud.common.utils.RedisUtil;
import pers.miracle.miraclecloud.system.entity.User;
import pers.miracle.miraclecloud.system.mapper.UserMapper;
import pers.miracle.miraclecloud.system.service.IUserService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: 蔡奇峰
 * @date: 2020/8/10 下午5:02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper mapper;

    private RedisUtil redisUtil = new RedisUtil();

    /**
     * 登录验证
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public String login(String userName, String password) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_name", userName);
        wrapper.eq("password", password);

        User user = mapper.selectOne(wrapper);
        if (null != user) {
            String token = redisUtil.getStr(user.getUserId() + "");

            // 不存在这个token 即第一次登录或者过期删除
            if (StringUtils.isEmpty(token)) {
                // 保证redis和jwt设置过期时间相同
                Calendar calendar = Calendar.getInstance();
                // 设置过期时间
                calendar.add(Calendar.HOUR, 24 * 6);
                // 过期时间
                Date expireTime = calendar.getTime();
                // 获取过期时间的时间戳
                long time = expireTime.getTime();
                // 创建jwt
                token = JwtUtil.createToken(user.getUserId() , user.getUserName(), expireTime);
                // 存jwt到redis过期时间6天
                redisUtil.setToken(user.getUserId() + "", token, time);
            }

            return token;
        }else {
            throw new RuntimeException("用户名或密码错误！");
        }


    }

    @Override
    public List<User> listByUser(User user) {

        return mapper.listByUser(user);
    }
}
