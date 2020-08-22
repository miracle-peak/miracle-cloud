package pers.miracle.miraclecloud.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.miracle.miraclecloud.system.entity.Menu;
import pers.miracle.miraclecloud.system.mapper.MenuMapper;
import pers.miracle.miraclecloud.system.service.IMenuService;

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

    @Override
    public List<Menu> ListByMenu(Menu menu) {
        return menuMapper.ListByMenu(menu);
    }

    @Override
    public List<Menu> listByRole(Collection<String> roleIds) {
        return buildMenuTree(menuMapper.listByRole(roleIds), null);
    }


    /**
     * 构建菜单树
     *
     * @param menuList
     * @param pId
     * @return
     */
    public List<Menu> buildMenuTree(List<Menu> menuList, Long pId) {
        // 菜单树
        List<Menu> menuTree = new ArrayList<>();
        for (int i = 0; i < menuList.size(); i++) {
            Menu menu = menuList.get(i);
            if (menu.getParentId() == 0) {
                // 移除父节点避免死循环
                menuList.remove(i);
                menu.setChildren(buildMenuTree(menuList, menu.getId()));
                // 把父节点及其子节点添加到菜单树
                menuTree.add(menu);
            } else {
                if (pId != null && pId.equals(menu.getParentId())) {
                    // 继续递归查询当前菜单是否有子菜单
                    menu.setChildren(buildMenuTree(menuList, menu.getId()));
                    menuTree.add(menu);
                }
            }
        }

        return menuTree;
    }


}
