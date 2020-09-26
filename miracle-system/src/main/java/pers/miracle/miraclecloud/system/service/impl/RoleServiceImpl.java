package pers.miracle.miraclecloud.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import pers.miracle.miraclecloud.system.entity.Menu;
import pers.miracle.miraclecloud.system.entity.Role;
import pers.miracle.miraclecloud.system.mapper.MenuMapper;
import pers.miracle.miraclecloud.system.mapper.RoleMapper;
import pers.miracle.miraclecloud.system.service.IRoleService;
import pers.miracle.miraclecloud.system.vo.RoleMenuVO;

import java.util.Arrays;
import java.util.List;


/**
 * @author: 蔡奇峰
 * @date: 2020/8/12 下午4:23
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MenuMapper menuMapper;

    /**
     * 添加角色并绑定其菜单
     *
     * @param vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRole(RoleMenuVO vo) {
        save(vo);
        if (!CollectionUtils.isEmpty(vo.getMenus())) {
            roleMapper.bindMenu(vo.getRoleId(), vo.getMenus());
        }
    }

    /**
     * 更新角色及菜单
     *
     * @param roleMenuVo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(RoleMenuVO roleMenuVo) {
        // 传入VO 报错：There is no getter for property named 'null' in （RoleMenuVO）
        // 但 save(vo); 方法直接传入VO没问题, 需要在实体主键加上@TableId注解
         updateById(roleMenuVo);

        roleMapper.clearMenu(roleMenuVo.getRoleId());
        if (!CollectionUtils.isEmpty(roleMenuVo.getMenus())) {
            roleMapper.bindMenu(roleMenuVo.getRoleId(), roleMenuVo.getMenus());
        }
    }

    /**
     * 获取角色及其菜单
     *
     * @param roleId
     * @return
     */
    @Override
    public RoleMenuVO getRole(String roleId) {
        // 查询该角色信息
        Role role = query().eq("role_id", roleId)
                .oneOpt()
                .orElseThrow(() -> new RuntimeException("未查询到该角色!"));
        // Assert.notNull(role, "未查找到该角色");

        RoleMenuVO roleMenuVO = new RoleMenuVO();
        roleMenuVO.setName(role.getName());
        roleMenuVO.setLocked(role.getLocked());
        roleMenuVO.setRoleId(roleId);
        // 查询该角色菜单
        List<Menu> menuList = menuMapper.listByRole(Arrays.asList(roleId));
        // 返回未构建成树的菜单
        roleMenuVO.setMenus(menuList);
        // roleMenuVO.setMenus(RoleMenuVO.buildMenuTree(menuList, null));

        return roleMenuVO;
    }

    /**
     * 清空角色的菜单
     *
     * @param roleId
     */
    @Override
    public void clearMenu(String roleId) {

        roleMapper.clearMenu(roleId);
    }

    /**
     * 删除角色并清空其菜单
     *
     * @param roleIds
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleAndMenu(String[] roleIds) {
        for (String roleId : roleIds) {
            int isDelete = roleMapper.deleteRole(roleId);
            // 返回影响行数 <=0 说明删除失败
            if (isDelete <= 0) {
                throw new RuntimeException("删除失败");
            }
        }
        // TODO 在controller层removeByIds及service层使用deleteBatchIds，删除失败，原因未知
        // removeByIds(Arrays.asList(roleIds));

        for (String id : roleIds) {
            clearMenu(id);
        }
    }

    /**
     * 仅删除角色
     *
     * @param roleIds
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteRole(String[] roleIds) {
        for (String roleI : roleIds) {
            int isDelete = roleMapper.deleteRole(roleI);
            // 返回影响行数 <=0 说明删除失败
            if (isDelete <= 0) {
                return false;
            }
        }

        return true;
    }

}
