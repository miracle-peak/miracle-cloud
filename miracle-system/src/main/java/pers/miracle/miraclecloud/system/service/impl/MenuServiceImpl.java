package pers.miracle.miraclecloud.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.miracle.miraclecloud.system.entity.Menu;
import pers.miracle.miraclecloud.system.mapper.MenuMapper;
import pers.miracle.miraclecloud.system.service.IMenuService;

/**
 * @author: 蔡奇峰
 * @date: 2020/8/14 下午4:43
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
}
