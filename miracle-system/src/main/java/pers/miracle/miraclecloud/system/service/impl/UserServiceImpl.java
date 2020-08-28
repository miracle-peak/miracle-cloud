package pers.miracle.miraclecloud.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import pers.miracle.miraclecloud.common.utils.JwtUtil;
import pers.miracle.miraclecloud.common.utils.RedisUtil;
import pers.miracle.miraclecloud.system.entity.Role;
import pers.miracle.miraclecloud.system.entity.User;
import pers.miracle.miraclecloud.system.mapper.UserMapper;
import pers.miracle.miraclecloud.system.service.IUserService;
import pers.miracle.miraclecloud.system.vo.UserRoleVO;

import javax.annotation.Resource;
import java.util.Arrays;
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

    @Resource
    private RedisUtil redisUtil;

    /**
     * 登录验证
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public String login(String userName, String password) {
        /*User one = query().eq("user_name", userName)
                .eq("password", password)
                //.one();
                .oneOpt()
                .orElseThrow(() -> new RuntimeException("登录失败！用户名或密码错误！"));*/

        User user = mapper.getOne(userName, password);
        if (null != user) {
            String token = redisUtil.getStr(user.getUserId() + "");

            // 不存在这个token 即第一次登录或者过期删除
            if (StringUtils.isEmpty(token)) {
                // 保证redis和jwt设置过期时间相同
                Calendar calendar = Calendar.getInstance();
                // 设置过期时间
                calendar.add(Calendar.HOUR, 24 * 1);
                // 过期时间
                Date expireTime = calendar.getTime();
                // 获取过期时间的时间戳, 使用同一个 expireTime 保证 jwt 和 redis key过期时间一致
                long time = expireTime.getTime() - System.currentTimeMillis();
                // 创建jwt
                token = JwtUtil.createToken(user.getUserId(), user.getUserName(), expireTime);
                // 存jwt到redis过期时间6天
                redisUtil.setToken(user.getUserId() + "", token, time);
            }

            return token;
        } else {
            throw new RuntimeException("用户名或密码错误！");
        }


    }

    /**
     * 添加/注册用户并绑定角色
     *
     * @param vo
     */
    @Override
    public void addUser(UserRoleVO vo) {
        save(vo);

        if (!CollectionUtils.isEmpty(vo.getRoles())) {
            mapper.bingRole(vo.getUserId(), vo.getRoles());
        }
    }

    /**
     * 更新用户及其角色
     *
     * @param vo
     */
    @Override
    public void updateRole(UserRoleVO vo) {
        updateById(vo);
        mapper.clearRole(vo.getUserId());
        if (!CollectionUtils.isEmpty(vo.getRoles())) {
            mapper.bingRole(vo.getUserId(), vo.getRoles());
        }
    }

    /**
     * 删除用户并清空其角色
     *
     * @param ids
     */
    @Override
    public void deleteRole(String[] ids) {
        removeByIds(Arrays.asList(ids));

        for (String id : ids) {
            mapper.clearRole(id);
        }
    }

    @Override
    public List<String> rolesByUserId(String userId) {
        return mapper.rolesByUserId(userId);
    }

    @Override
    public List<User> listByUser(User user) {

        return mapper.listByUser(user);
    }


}
