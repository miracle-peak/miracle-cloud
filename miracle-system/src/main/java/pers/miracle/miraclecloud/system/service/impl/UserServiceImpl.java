package pers.miracle.miraclecloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pers.miracle.miraclecloud.common.utils.Md5Util;
import pers.miracle.miraclecloud.common.utils.R;
import pers.miracle.miraclecloud.system.entity.User;
import pers.miracle.miraclecloud.system.mapper.UserMapper;
import pers.miracle.miraclecloud.system.service.IUserService;

/**
 * @author: 蔡奇峰
 * @date: 2020/8/10 下午5:02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper mapper;

    @Override
    public String login(String userName, String password) {
        if(StringUtils.isEmpty(userName)){
            throw new RuntimeException("请输入用户名!");
        }else if (StringUtils.isEmpty(password)){
           throw new RuntimeException("请输入密码!");
        }
        // 密码加盐加密
        password = Md5Util.saltEncryption(password);

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_name", userName);
        wrapper.eq("password", password);

        User user = mapper.selectOne(wrapper);

        return null;
    }
}
