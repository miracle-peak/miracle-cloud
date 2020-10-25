package pers.miracle.miraclecloud.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pers.miracle.miraclecloud.common.annotation.Log;
import pers.miracle.miraclecloud.common.annotation.VisitLimit;
import pers.miracle.miraclecloud.common.utils.JwtUtil;
import pers.miracle.miraclecloud.common.utils.Md5Util;
import pers.miracle.miraclecloud.common.utils.PageInfo;
import pers.miracle.miraclecloud.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import pers.miracle.miraclecloud.system.entity.User;
import pers.miracle.miraclecloud.system.service.IUserService;
import pers.miracle.miraclecloud.system.vo.UserRoleVO;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.*;

/**
 * @author: 蔡奇峰
 * @date: 2020/8/10 下午5:04
 */
@RestController
@RequestMapping("/system/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService service;

    @Autowired
    private HttpServletRequest request;


    /**
     * 登录
     * 限制多次登录，8秒内请求超过4即限制60秒不能登录
     *
     * @param user
     * @return
     */
    @Log
    @VisitLimit(limit = 4, rangeTime = 8, limitTime = 60)
    @PostMapping("/login")
    public R login(@RequestBody User user) {
        // 密码加盐加密
        String password = Md5Util.saltEncryption(user.getPassword());
        // 获取or创建当前用户token
        String token = service.login(user.getUserName(), password);

        return R.ok(token);
    }

    /**
     * 根据jwt获取用户的角色
     *
     * @return
     */
    // @SentinelResource(value = "sentinelFall", fallback = "dealException")
    @GetMapping("/getRoles")
    public R getRoles() {
        // 获取当前用户的userId
        String userId = JwtUtil.getUserIdByJwt(request);

        List<String> userIds = service.rolesByUserId(userId);
        return R.ok(userIds);
    }


    /**
     * 查询全部用户
     *
     * @return
     */
    @GetMapping("/list")
    public R list(User user, @RequestParam(value = "pageSize", required = false) Integer pageSize,
                  @RequestParam(value = "pageNum", required = false) Integer pageNum) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (null != user) {
            if (StringUtils.isEmpty(user.getLocked()) && !StringUtils.isEmpty(user.getUserName())) {
                // 仅根据名称查询
                queryWrapper.like("user_name", user.getUserName());
            } else if (!StringUtils.isEmpty(user.getLocked()) && StringUtils.isEmpty(user.getUserName())) {
                // 仅根据是否锁定状态查询
                queryWrapper.eq("locked", user.getLocked());

            } else if (!StringUtils.isEmpty(user.getLocked()) && !StringUtils.isEmpty(user.getUserName())) {
                // 根据名称及是否锁定状态查询
                queryWrapper.eq("locked", user.getLocked());
                queryWrapper.like("user_name", user.getUserName());
            }
        }
        if (null == pageSize || null == pageNum) {
            return R.ok(service.listByUser(user));
        } else {
            Page<User> rolePage = new Page<User>().setSize(pageSize).setCurrent(pageNum);
            Page page = service.page(rolePage, queryWrapper);

            PageInfo pageInfo = new PageInfo(page.getTotal(), page.getSize(),
                    page.getCurrent(), page.getRecords());

            return R.ok(pageInfo);
        }
    }

    /**
     * 查询用户信息及其角色
     *
     * @param userId
     * @return
     */
    @GetMapping("/getUser/{userId}")
    public R getUser(@PathVariable("userId") String userId) {

        return R.ok(service.getById(userId)).data("roleIds", service.rolesByUserId(userId));
    }

    /**
     * 修改用户并更新其角色
     *
     * @param vo
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody UserRoleVO vo) {
        service.updateRole(vo);
        return R.ok();
    }

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    @PostMapping("/edit")
    public R edit(@RequestBody User user) {
        return service.updateById(user) ? R.ok() : R.error();
    }

    /**
     * 批量删除用户并清空其角色（删除单个也可用）
     *
     * @param ids
     * @return
     */
    @PostMapping("/remove")
    public R remove(@RequestBody String[] ids) {
        service.deleteRole(ids);
        return R.ok();
    }

    /**
     * 批量删除用户（删除单个也可用）
     *
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    public R delete(@RequestBody String[] ids) {
        return service.removeByIds(Arrays.asList(ids)) ? R.ok() : R.error();
    }

    /**
     * 添加/注册用户并绑定角色
     *
     * @param user
     * @return
     */
    @PostMapping("/add")
    public R add(@RequestBody UserRoleVO user) {
        // 密码加盐加密
        user.setPassword(Md5Util.saltEncryption(user.getPassword()));
        // 生成用户唯一id,并发大的时候可能重复, 可用IdUtil.simpleUUID()替代 博主简化处理在不创建creat_time字段,还可以知道时间
        LocalDate time = LocalDate.now();
        Integer num = new Random().nextInt(99999);
        String id = time + "-" + (System.currentTimeMillis() + "").substring(7) + num;
        user.setUserId(id);
        service.addUser(user);
        return R.ok();
    }


}
