package pers.miracle.miraclecloud.system.vo;

import pers.miracle.miraclecloud.system.entity.Menu;
import pers.miracle.miraclecloud.system.entity.Role;

import java.util.List;

/**
 * @author: 蔡奇峰
 * @date: 2020/8/14 下午4:54
 */
public class RoleMenuVO extends Role {

    private List<Menu> menus;

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        return "RoleMenuVO{" +
                "menus=" + menus +
                '}';
    }
}
