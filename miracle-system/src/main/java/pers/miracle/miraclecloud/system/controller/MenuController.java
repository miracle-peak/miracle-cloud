package pers.miracle.miraclecloud.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.miracle.miraclecloud.common.utils.R;
import pers.miracle.miraclecloud.system.entity.Menu;
import pers.miracle.miraclecloud.system.entity.Role;
import pers.miracle.miraclecloud.system.service.IMenuService;

/**
 * @author: 蔡奇峰
 * @date: 2020/8/14 下午4:46
 */
@RequestMapping("/system/menu")
@RestController
public class MenuController {
    @Autowired
    private IMenuService service;

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
    public R list(@RequestBody Menu menu) {

        return R.ok(service.ListByMenu(menu));
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
     * 获取角色对应菜单
     *
     * @param roleId
     * @return
     */
    @GetMapping("/menuTree/{roleId}")
    public R menuTreeByRole(@PathVariable("roleId") String roleId) {

        return R.ok();
    }
}
