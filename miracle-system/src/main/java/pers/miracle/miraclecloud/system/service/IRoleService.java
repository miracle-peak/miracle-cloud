package pers.miracle.miraclecloud.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.miracle.miraclecloud.system.entity.Role;
import pers.miracle.miraclecloud.system.vo.RoleMenuVO;

import java.util.List;

/**
 * @author: 蔡奇峰
 * @date: 2020/8/12 下午4:23
 */
public interface IRoleService extends IService<Role> {

    /**
     * 添加角色并绑定其菜单
     *
     * @param roleMenuVo
     */
    void addRole(RoleMenuVO roleMenuVo);

    /**
     * 更新角色及菜单
     *
     * @param roleMenuVo
     */
    void updateRole(RoleMenuVO roleMenuVo);

    /**
     * 获取角色及其菜单
     *
     * @param roleId
     * @return
     */
    RoleMenuVO getRole(String roleId);


    /**
     * 清空角色的菜单
     *
     * @param roleId
     */
    void clearMenu(String roleId);


    /**
     * 删除角色并清空其菜单
     *
     * @param roleIds
     */
    void deleteRole(String[] roleIds);
}
