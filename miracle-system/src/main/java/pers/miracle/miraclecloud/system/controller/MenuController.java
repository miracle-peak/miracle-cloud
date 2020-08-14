package pers.miracle.miraclecloud.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.miracle.miraclecloud.common.utils.R;
import pers.miracle.miraclecloud.system.entity.Menu;
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

}
