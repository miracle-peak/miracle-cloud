package pers.miracle.miraclecloud.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pers.miracle.miraclecloud.system.entity.Menu;
import pers.miracle.miraclecloud.system.entity.Role;

import java.util.List;

/**
 * @author: 蔡奇峰
 * @date: 2020/8/12 下午4:24
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 绑定角色菜单
     *
     * @param roleId
     * @param menus
     */
    void bindMenu(@Param("roleId") String roleId, @Param("menus") List<Menu> menus);

    /**
     * 清空角色菜单
     *
     * @param roleId
     */
    void clearMenu(@Param("roleId") String roleId);

    /**
     * 仅删除角色
     *
     * @param roleIds
     * @return
     */
    int deleteRole(@Param("roleId") String roleIds);
}
