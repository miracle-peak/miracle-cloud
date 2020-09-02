package pers.miracle.miraclecloud.system.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pers.miracle.miraclecloud.common.annotation.Log;
import pers.miracle.miraclecloud.common.utils.JwtUtil;
import pers.miracle.miraclecloud.common.utils.Md5Util;
import pers.miracle.miraclecloud.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import pers.miracle.miraclecloud.system.entity.User;
import pers.miracle.miraclecloud.system.service.IUserService;
import pers.miracle.miraclecloud.system.vo.UserRoleVO;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author: 蔡奇峰
 * @date: 2020/8/10 下午5:04
 */
@RestController
@RequestMapping("/system/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(User.class);

    @Autowired
    private IUserService service;

    @Autowired
    private HttpServletRequest request;


    /**
     * 登录
     *
     * @param user
     * @return
     */
    @Log
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
    public R getRoles(){
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
    public R list(User user, @RequestParam("pageSize") Integer pageSize,
                  @RequestParam("pageNum") Integer pageNum) {

        List<User> list = service.listByUser(user);
        return R.ok(list);
    }

    @GetMapping("/getUser/{userId}")
    public R getUser(@PathVariable("userId") String userId){

        return R.ok(service.getById(userId));
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
        String password = Md5Util.saltEncryption(user.getPassword());
        user.setPassword(password);
        // 生成用户唯一id,并发大的时候可能重复, 可用IdUtil.simpleUUID()替代 博主简化处理在不创建creat_time字段,还可以知道时间
        LocalDate time = LocalDate.now();
        Integer num = new Random().nextInt(99999);
        String id = time + "-" + (System.currentTimeMillis() + "").substring(7) + num;
        user.setUserId(id);

        service.addUser(user);
        return R.ok();
    }


}
