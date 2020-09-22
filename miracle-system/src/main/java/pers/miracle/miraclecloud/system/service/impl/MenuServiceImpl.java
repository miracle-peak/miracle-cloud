package pers.miracle.miraclecloud.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.miracle.miraclecloud.system.entity.Menu;
import pers.miracle.miraclecloud.system.mapper.MenuMapper;
import pers.miracle.miraclecloud.system.service.IMenuService;
import pers.miracle.miraclecloud.system.vo.RoleMenuVO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author: 蔡奇峰
 * @date: 2020/8/14 下午4:43
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 查询全部菜单
     *
     * @param menu
     * @return
     */
    @Override
    public List<Menu> ListByMenu(Menu menu) {
        // 构建菜单树
        List<Menu> menuTree = RoleMenuVO.buildMenuTree(menuMapper.ListByMenu(menu));
        return menuTree;
    }

    /**
     * 根据角色查询菜单
     *
     * @param roleIds
     * @return
     */
    @Override
    public List<Menu> listByRole(Collection<String> roleIds) {
        return RoleMenuVO.buildMenuTree(menuMapper.listByRole(roleIds));
    }


}
