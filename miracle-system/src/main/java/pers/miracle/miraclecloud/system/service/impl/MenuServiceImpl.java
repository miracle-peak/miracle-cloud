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

    /**
     * 查询全部菜单
     *
     * @param menu
     * @return
     */
    @Override
    public List<Menu> ListByMenu(Menu menu) {
        // 构建菜单树
        List<Menu> menuTree = buildMenuTree(menuMapper.ListByMenu(menu), null);
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
        return buildMenuTree(menuMapper.listByRole(roleIds), null);
    }


    /**
     * 利用递归构建菜单树
     * <p>
     * 不要在 foreach 循环里进行元素的 remove/add 操作,应使用迭代器 Iterator，或者直接使用普通for循环进行操作;
     * modCount 意为 list 的结构变化次数，而 expectedModCount 可被视为 Iterator 内部记录的集合结构变化次数;
     * 如果这两个不相等则抛 ConcurrentModificationException 异常，这就是java错误检查机制：fail-fast(快速失败);
     * foreach 循环其内部实现利用了 Iterator;
     * <p>
     * fail-fast， 即快速失败，它是Java集合的一种错误检测机制，
     * 当多个线程对集合(非fail-safe的集合类)进行结构上的改变的时候，
     * 有可能会产生fail-fast机制，这个时候就会抛出 ConcurrentModificationException
     * (当方法检测到对象的并发修改，但不允许这种修改的时候就抛出该异常)。同时需要注意的是，
     * 即使不是多线程环境，如果单线程违反了规则，同样也会抛出该异常。
     *
     * @param menuList 未构建成树的菜单集合
     * @param pId      父节点id，调用该方法时，可传入null
     * @return
     */
    public List<Menu> buildMenuTree(List<Menu> menuList, Long pId) {
        // 菜单树
        List<Menu> menuTree = new ArrayList<>();

        for (int i = 0; i < menuList.size(); i++) {
            Menu menu = menuList.get(i);
            // 如果parentId=0即没有父节点
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
