package pers.miracle.miraclecloud.system.vo;

import pers.miracle.miraclecloud.system.entity.Menu;
import pers.miracle.miraclecloud.system.entity.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 蔡奇峰
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

    public RoleMenuVO() {
    }

    @Override
    public String toString() {
        return "RoleMenuVO{" +
                "menus=" + menus +
                '}';
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
    public static List<Menu> buildMenuTree(List<Menu> menuList) {
        // 菜单树
        List<Menu> menuTree = new ArrayList<>();

        for (Menu menu : menuList) {
            if (menu.getParentId() == 0) {
                menu.setChildren(findChildren(menuList, menu.getId()));
                menuTree.add(menu);
            }
        }

        return menuTree;
    }

    /**
     * 获取子节点
     *
     * @param menuList
     * @param pId
     * @return
     */
    public static List<Menu> findChildren(List<Menu> menuList, Long pId) {
        // 菜单树
        List<Menu> menuTree = new ArrayList<>();
        for (Menu menu : menuList) {
            if (pId.equals(menu.getParentId())) {
                menu.setChildren(findChildren(menuList, menu.getId()));
                menuTree.add(menu);
            }
        }

        return menuTree;
    }
}
