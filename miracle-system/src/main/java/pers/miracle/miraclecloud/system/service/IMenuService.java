package pers.miracle.miraclecloud.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.miracle.miraclecloud.system.entity.Menu;

import java.util.Collection;
import java.util.List;

/**
 * @author: 蔡奇峰
 * @date: 2020/8/14 下午4:43
 */
public interface IMenuService extends IService<Menu> {
    /**
     * 查询菜单列表
     *
     * @param menu
     * @return
     */
    List<Menu> ListByMenu(Menu menu);

    /**
     * 根据角色查询菜单
     *
     * @param roleIds
     * @return
     */
    List<Menu> listByRole(Collection<String> roleIds);
}
