package pers.miracle.miraclecloud.system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pers.miracle.miraclecloud.common.utils.R;
import pers.miracle.miraclecloud.system.entity.Role;
import pers.miracle.miraclecloud.system.service.IRoleService;
import pers.miracle.miraclecloud.system.vo.RoleMenuVO;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author: 蔡奇峰
 * @date: 2020/8/12 下午4:26
 */
@RequestMapping("/system/role")
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
     * 修改角色并更新其菜单
     *
     * @param vo
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody RoleMenuVO vo) {
        service.updateRole(vo);
        return R.ok();
    }

    /**
     * 批量删除角色并清空菜单（删除单个也可用）
     *
     * @param roleIds
     * @return
     */
    @PostMapping("/remove")
    public R remove(@RequestBody String[] roleIds){
        service.deleteRole(roleIds);
        return R.ok();
    }

    /**
     * 批量删除角色（删除单个也可用）
     *
     * @param roleIds
     * @return
     */
    @PostMapping("/delete")
    public R delete(@RequestBody String[] roleIds) {
        return service.removeByIds(Arrays.asList(roleIds)) ? R.ok() : R.error();
    }

    /**
     * 添加角色并绑定菜单
     *
     * @param role
     * @return
     */
    @PostMapping("/add")
    public R add(@RequestBody RoleMenuVO role) {
        // 生成角色唯一id,并发大的时候可能重复, 可用IdUtil.simpleUUID()替代 博主简化处理在不创建creat_time字段,还可以知道时间
        Integer num = new Random().nextInt(99999);
        LocalDate time = LocalDate.now();
        String id = time + "-" + (System.currentTimeMillis() + "").substring(7) + num;
        role.setRoleId(id);
        // 添加角色并绑定菜单
        service.addRole(role);
        return R.ok();
    }

}
