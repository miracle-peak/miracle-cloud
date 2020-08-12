package pers.miracle.miraclecloud.system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pers.miracle.miraclecloud.common.utils.R;
import pers.miracle.miraclecloud.system.entity.Role;
import pers.miracle.miraclecloud.system.service.IRoleService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author: 蔡奇峰
 * @date: 2020/8/12 下午4:26
 */
@RequestMapping("/role")
@RestController
public class RoleController {
    private static final Logger log = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private IRoleService service;

    /**
     * 查询角色
     *
     * @param role
     * @return
     */
    @GetMapping("/list")
    public R list(Role role) {
        List<Role> list;
        if (null == role || StringUtils.isEmpty(role.getLocked())) {
            list = StringUtils.isEmpty(role.getName()) ?
                    service.list() : service.query().like("name", role.getName())
                    .list();
        } else {
            list = service.query().eq("locked", role.getLocked())
                    .list();
        }
        return R.ok(list);
    }

    /**
     * 修改角色
     *
     * @param role
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody Role role) {
        return service.updateById(role) ? R.ok() : R.error();
    }

    /**
     * 批量删除角色（删除单个也可用）
     *
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    public R delete(@RequestBody String[] ids){
        return service.removeByIds(Arrays.asList(ids)) ? R.ok() : R.error();
    }

    /**
     * 添加角色
     *
     * @param role
     * @return
     */
    @PostMapping("/add")
    public R add(@RequestBody Role role) {
        // 生成角色唯一id
        Integer num = new Random().nextInt(18000);
        LocalDate time = LocalDate.now();
        String id = time + "-" + (System.currentTimeMillis() + "").substring(7) + num;
        role.setRoleId(id);

        return service.save(role) ? R.ok() : R.error("添加角色失败！请重试");
    }

}
