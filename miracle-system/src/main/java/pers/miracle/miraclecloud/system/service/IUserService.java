package pers.miracle.miraclecloud.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.miracle.miraclecloud.system.entity.User;
import java.util.List;

/**
 * @author: 蔡奇峰
 * @date: 2020/8/10 下午5:00
 */
public interface IUserService extends IService<User> {

    /**
     * 登录验证
     *
     * @param userName
     * @param password
     * @return
     */
    String login(String userName, String password);


    /**
     * 查询用户
     *
     * @param user
     * @return
     */
    List<User> listByUser(User user);
}
