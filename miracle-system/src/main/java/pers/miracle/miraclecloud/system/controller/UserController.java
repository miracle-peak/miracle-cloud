package pers.miracle.miraclecloud.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.miracle.miraclecloud.common.annotation.Log;
import pers.miracle.miraclecloud.common.utils.Md5Util;
import pers.miracle.miraclecloud.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pers.miracle.miraclecloud.system.entity.User;
import pers.miracle.miraclecloud.system.service.IUserService;

import java.util.List;

/**
 * @author: 蔡奇峰
 * @date: 2020/8/10 下午5:04
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService service;


    /**
     * 登录
     *
     * @param user
     * @return
     */
    @PostMapping("/login")
    @Log
    public R login(@RequestBody User user){
        String userName = user.getUserName();
        String password = user.getPassword();

        String token = service.login(userName, password);

        return R.ok();
    }


}
