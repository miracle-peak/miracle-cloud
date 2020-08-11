package pers.miracle.miraclecloud.system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pers.miracle.miraclecloud.common.annotation.Log;
import pers.miracle.miraclecloud.common.utils.Md5Util;
import pers.miracle.miraclecloud.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import pers.miracle.miraclecloud.system.entity.User;
import pers.miracle.miraclecloud.system.service.IUserService;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

/**
 * @author: 蔡奇峰
 * @date: 2020/8/10 下午5:04
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(User.class);

    @Autowired
    private IUserService service;


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
        return R.ok(token).data("role");
    }

    /**
     * 查询全部用户
     *
     * @return
     */
    @GetMapping("/list")
    public R list(User user) {
        List<User> list = service.listByUser(user);

        log.info("list:{}",list.toString());
        return R.ok(list);
    }


    /**
     * 添加/注册用户
     *
     * @param user
     * @return
     */
    @PostMapping("/add")
    public R add(@RequestBody User user){
        // 密码加盐加密
        String password = Md5Util.saltEncryption(user.getPassword());
        user.setPassword(password);
        LocalDate time = LocalDate.now();
        // 生成用户唯一id
        Integer num = new Random().nextInt(18000);
        String id = time + "-" +  (System.currentTimeMillis() + "").substring(7) + num;

        user.setUserId(id);
        boolean flag = service.save(user);
        if (! flag){
            return R.error();
        }
        return R.ok();
    }


}
