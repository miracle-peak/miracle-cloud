package pers.miracle.miraclecloud.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.miracle.miraclecloud.system.entity.Role;
import pers.miracle.miraclecloud.system.mapper.RoleMapper;
import pers.miracle.miraclecloud.system.service.IRoleService;

/**
 * @author: 蔡奇峰
 * @date: 2020/8/12 下午4:23
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
}
