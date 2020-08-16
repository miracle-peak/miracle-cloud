package pers.miracle.miraclecloud.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import pers.miracle.miraclecloud.system.entity.Role;
import pers.miracle.miraclecloud.system.entity.User;
import pers.miracle.miraclecloud.system.vo.UserRoleVO;

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

    /**
     * 添加/注册用户并绑定角色
     *
     * @param vo
     */
    void addUser(UserRoleVO vo);

    /**
     * 更新用户及其角色
     *
     * @param vo
     */
    void updateRole(UserRoleVO vo);

    /**
     * 删除用户并清空其角色
     *
     * @param ids
     */
    void deleteRole(String[] ids);

    /**
     * 查询用户的角色
     *
     * @param userId
     * @return
     */
    List<String> rolesByUserId(String userId);
}
