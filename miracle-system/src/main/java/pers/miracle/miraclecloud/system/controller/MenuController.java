package pers.miracle.miraclecloud.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.miracle.miraclecloud.common.utils.JwtUtil;
import pers.miracle.miraclecloud.common.utils.R;
import pers.miracle.miraclecloud.system.entity.Menu;
import pers.miracle.miraclecloud.system.service.IMenuService;
import pers.miracle.miraclecloud.system.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author: 蔡奇峰
 * @date: 2020/8/14 下午4:46
 */
@RequestMapping("/system/menu")
@RestController
public class MenuController {
    @Autowired
    private IMenuService service;
    @Autowired
    private IUserService userService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 添加菜单
     *
     * @param menu
     * @return
     */
    @PostMapping("/add")
    public R add(@RequestBody Menu menu) {
        return service.save(menu) ? R.ok() : R.error();
    }

    /**
     * 查询菜单列表
     *
     * @param menu
     * @return
     */
    @PostMapping("/list")
    public R list(@RequestBody(required = false) Menu menu) {

        return R.ok(service.ListByMenu(menu));
    }

    /**
     * 删除菜单
     *
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    public R delete(@RequestBody String[] ids) {
        return R.ok(service.removeByIds(Arrays.asList(ids)));
    }

    /**
     * 修改菜单
     *
     * @param menu
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody Menu menu) {
        return service.updateById(menu) ? R.ok() : R.error();
    }


    /**
     * 查询某个角色对应菜单
     *
     * @param roleId
     * @return
     */
    @GetMapping("/menuByRole/{roleId}")
    public R menuTreeByRole(@PathVariable("roleId") String roleId) {
        List<Menu> menus = service.listByRole(new ArrayList<>(Arrays.asList(roleId)));

        return R.ok(menus);
    }

    /**
     * 获取用户对应菜单
     *
     * @param userId
     * @return
     */
    @GetMapping("/menuTree/{userId}")
    public R menuTreeByUser(@PathVariable("userId") String userId) {
        List<String> roleIds = userService.rolesByUserId(userId);
        if(roleIds.size() == 0){
            return R.error("对不起，该用户无角色权限！");
        }
        List<Menu> menus = service.listByRole(roleIds);

        return R.ok(menus);
    }

    /**
     * 获取角色对应菜单
     *
     * @return
     */
    @GetMapping("/menuTree")
    public R menuTree() {
        // 获取当前用户的userId
        String userId = JwtUtil.getUserIdByJwt(request);

        List<String> roleIds = userService.rolesByUserId(userId);
        if(roleIds.size() == 0){
            return R.error("对不起，该用户无角色权限！");
        }
        List<Menu> menus = service.listByRole(roleIds);

        return R.ok(menus);
    }

}
